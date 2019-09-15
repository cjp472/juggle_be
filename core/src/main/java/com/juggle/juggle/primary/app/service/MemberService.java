package com.juggle.juggle.primary.app.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.account.dao.MemberAccountDao;
import com.juggle.juggle.primary.account.service.MemberAccountService;
import com.juggle.juggle.primary.app.dao.MemberDao;
import com.juggle.juggle.primary.app.dto.MemberDto;
import com.juggle.juggle.primary.app.dto.MemberModifyDto;
import com.juggle.juggle.primary.app.dto.MemberTeam;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.account.model.MemberAccount;
import com.juggle.juggle.primary.auth.dao.MemberAuthDao;
import com.juggle.juggle.primary.auth.model.MemberAuth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService extends BaseCRUDService<Member> {
    @Autowired
    private MemberDao dao;

    @Autowired
    private MemberAuthDao memberAuthDao;

    @Autowired
    private MemberAccountDao memberAccountDao;

    @Autowired
    private MemberAccountService memberAccountService;

    @Override
    protected IRepo<Member> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(Member entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(getCode());
        }
        if(StringUtils.isEmpty(entity.getNickName())){
            entity.setNickName("来呗用户"+entity.getCode().substring(0,3));
        }
        entity.setGrade(Constants.MEMBER_GRADE.GRADE1);
        entity.setStatus(Constants.MEMBER_STATUS.NORMAL);
    }

    @Override
    protected void afterCreated(Member entity) {
        MemberAccount memberAccount = new MemberAccount();
        memberAccount.setMemberId(entity.getId());
        memberAccount.setAmount(BigDecimal.ZERO);
        memberAccountService.create(memberAccount);
    }

    public void normal(Long id){
       Member member = dao.findOne(id);
       member.setStatus(Constants.MEMBER_STATUS.NORMAL);
       update(id,member);
    }

    public void forbid(Long id){
        Member member = dao.findOne(id);
        member.setStatus(Constants.MEMBER_STATUS.FORBID);
        update(id,member);
    }

    public MemberDto profile(){
        Member member = (Member) UserSession.getAuthorize().getUser();
        MemberDto memberDto = new MemberDto();
        try{
            PropertyCopyUtil.getInstance().copyProperties(memberDto,member);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        MemberAccount memberAccount = memberAccountDao.findFirstByMemberId(member.getId());
        memberDto.setAmount(memberAccount.getAmount());
        MemberAuth memberAuth = memberAuthDao.findFirstByMemberId(member.getId());
        memberDto.setSetPay(null!=memberAuth.getPayPassword());
        return memberDto;
    }

    public MemberDto modify(MemberModifyDto dto){
        Member member = (Member) UserSession.getAuthorize().getUser();
        if(!StringUtils.isEmpty(dto.getAvatar())){
            member.setAvatar(dto.getAvatar());
        }
        if(!StringUtils.isEmpty(dto.getNickName())){
            member.setNickName(dto.getNickName());
        }
        member = update(member.getId(),member);
        MemberDto memberDto = new MemberDto();
        try{
            PropertyCopyUtil.getInstance().copyProperties(memberDto,member);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return memberDto;
    }

    @Cache
    public PageResult getTeam(PageSearch pageSearch,String grade){
        Member member = (Member) UserSession.getAuthorize().getUser();
        List<MemberTeam> memberTeams = new ArrayList<>();
        List<ValueFilter> filters = new ArrayList<>();
        filters.add(new ValueFilter("parentId","=",member.getId()));
        filters.add(new ValueFilter("grade","=", grade));
        pageSearch.setFilters(filters);
        PageResult pageResult = search(pageSearch);
        for (Object row : pageResult.getRows()) {
            Member child = (Member)row;
            MemberTeam memberTeam = new MemberTeam();
            try {
                PropertyCopyUtil.getInstance().copyProperties(memberTeam,child);
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析错误");
            }
            memberTeams.add(memberTeam);
        }
        pageResult.setRows(memberTeams);
        return pageResult;
    }

    public String getCode() {
        String code = "";
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] m = model.toCharArray();
        for (int j = 0; j < 6; j++) {
            char c = m[(int) (Math.random() * 36)];
            code = code + c;
        }
        boolean exists = dao.existsByCode(code);
        if(!exists){
            return code;
        }
        return getCode();
    }

    public void removeByMobile(String mobile){
        dao.deleteByMobile(mobile);
    }
}
