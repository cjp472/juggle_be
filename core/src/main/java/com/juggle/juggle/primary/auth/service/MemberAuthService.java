package com.juggle.juggle.primary.auth.service;

import com.juggle.juggle.common.util.TaobaoClientUtil;
import com.juggle.juggle.common.util.ValidationUtil;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.framework.common.utils.IpUtils;
import com.juggle.juggle.framework.common.utils.PasswordUtils;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.account.dao.MemberAccountDao;
import com.juggle.juggle.primary.account.model.MemberAccount;
import com.juggle.juggle.primary.app.dao.MemberDao;
import com.juggle.juggle.primary.app.dto.MemberDto;
import com.juggle.juggle.primary.app.model.InviteRecord;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.service.InviteRecordService;
import com.juggle.juggle.primary.app.service.MemberService;
import com.juggle.juggle.primary.auth.dao.AccessTokenDao;
import com.juggle.juggle.primary.auth.dao.MemberAuthDao;
import com.juggle.juggle.primary.auth.dto.MemberParent;
import com.juggle.juggle.primary.auth.dto.MemberProfile;
import com.juggle.juggle.primary.auth.model.AccessToken;
import com.juggle.juggle.primary.auth.model.MemberAuth;
import com.juggle.juggle.primary.setting.dao.DomainDao;
import com.taobao.api.response.TbkScPublisherInfoSaveResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class MemberAuthService extends BaseCRUDService<MemberAuth> {
    @Autowired
    private MemberAuthDao dao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private InviteRecordService inviteRecordService;

    @Autowired
    private MemberAccountDao memberAccountDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private DomainDao domainDao;

    @Override
    protected IRepo<MemberAuth> getRepo() {
        return dao;
    }

    public MemberProfile login(String mobile, String password, HttpServletRequest request){
        Member member = memberDao.findFirstByMobile(mobile);
        if(member==null){
            throw new ServiceException(1005,"账号或密码错误");
        }
        if(member.getStatus().equals(Constants.MEMBER_STATUS.FORBID)){
            throw new ServiceException(1005,"账号已被禁用");
        }
        MemberAuth memberAuth = dao.findFirstByMemberId(member.getId());
        if(!memberAuth.getPassword().equals(PasswordUtils.encript(password))){
            throw new ServiceException(1005,"账号或密码错误");
        }
        updateLast(memberAuth,request);
        AccessToken accessToken = updateToken(member);
        MemberProfile memberProfile = new MemberProfile();
        MemberDto memberDto = new MemberDto();
        try {
            PropertyCopyUtil.getInstance().copyProperties(memberDto,member);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析错误");
        }
        memberDto.setSetPay(null!=memberAuth.getPayPassword());
        MemberAccount memberAccount = memberAccountDao.findFirstByMemberId(member.getId());
        memberDto.setAmount(memberAccount.getAmount());
        memberProfile.setMember(memberDto);
        memberProfile.setToken(accessToken.getToken());
        return memberProfile;
    }

    public MemberProfile register(String inviteCode,String mobile,String password,Long domainId,HttpServletRequest request){
        boolean exist = memberDao.existsByMobile(mobile);
        if(exist){
            throw new ServiceException(1005,"手机号已经存在");
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setStatus(Constants.MEMBER_STATUS.NORMAL);
        if(!StringUtils.isEmpty(inviteCode)){
            if(ValidationUtil.isMobile(inviteCode)){
                Member parent = memberDao.findFirstByMobile(inviteCode);
                member.setParentId(parent.getId());
            }else{
                Member parent = memberDao.findFirstByCode(inviteCode);
                member.setParentId(parent.getId());
            }
        }
        member.setDomainId(domainId);
        member = memberService.create(member);
        if(null!=member.getParentId()){
            //增加邀请记录
            addInviteRecord(member.getParentId(),member.getId());
        }
        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setMemberId(member.getId());
        memberAuth.setPassword(PasswordUtils.encript(password));
        memberAuth = memberAuthService.create(memberAuth);
        updateLast(memberAuth,request);
        AccessToken accessToken = updateToken(member);
        MemberDto memberDto = new MemberDto();
        MemberProfile memberProfile = new MemberProfile();
        try {
            PropertyCopyUtil.getInstance().copyProperties(memberDto,member);
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析错误");
        }
        memberDto.setSetPay(false);
        memberDto.setAmount(BigDecimal.ZERO);
        memberProfile.setToken(accessToken.getToken());
        memberProfile.setMember(memberDto);
        return memberProfile;
    }

    public Member inviteRegister(String inviteCode,String mobile,String password,HttpServletRequest request){
        boolean exist = memberDao.existsByMobile(mobile);
        if(exist){
            throw new ServiceException(1005,"手机号已经存在");
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setStatus(Constants.MEMBER_STATUS.NORMAL);
        if(!StringUtils.isEmpty(inviteCode)){
            if(ValidationUtil.isMobile(inviteCode)){
                Member parent = memberDao.findFirstByMobile(inviteCode);
                member.setParentId(parent.getId());
                member.setDomainId(parent.getDomainId());
            }else{
                Member parent = memberDao.findFirstByCode(inviteCode);
                member.setParentId(parent.getId());
                member.setDomainId(parent.getDomainId());
            }
        }
        member = memberService.create(member);
        if(null!=member.getParentId()){
            //增加邀请记录
            addInviteRecord(member.getParentId(),member.getId());
        }
        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setPassword(PasswordUtils.encript(password));
        memberAuth.setMemberId(member.getId());
        memberAuth = memberAuthService.create(memberAuth);
        updateLast(memberAuth,request);
        return member;
    }

    public void setPayPassword(String payPassword){
        MemberAuth memberAuth = dao.findFirstByMemberId(Long.valueOf(UserSession.getAuthorize().getUserId().toString()));
        memberAuth.setPayPassword(PasswordUtils.encript(payPassword));
        update(memberAuth.getId(),memberAuth);
    }

    public boolean checkPayPassword(String payPassword){
        MemberAuth memberAuth = dao.findFirstByMemberId(Long.valueOf(UserSession.getAuthorize().getUserId().toString()));
        if(PasswordUtils.encript(payPassword).equals(memberAuth.getPayPassword())){
            return true;
        }
        return false;
    }

    public MemberParent parent(String inviteCode){
        Member member;
        if(ValidationUtil.isMobile(inviteCode)){
            member = memberDao.findFirstByMobile(inviteCode);
        }else{
            member = memberDao.findFirstByCode(inviteCode);
        }
        MemberParent memberParent = new MemberParent();
        if(null!=member){
            MemberDto memberDto = new MemberDto();
            try {
                PropertyCopyUtil.getInstance().copyProperties(memberDto,member);
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析错误");
            }
            memberParent.setMember(memberDto);
            if(null!=member.getDomainId()){
                memberParent.setDomainId(member.getDomainId());
            }else{
                memberParent.setDomainId(domainDao.findFirstByNameAndLevel("总部",3).getId());
            }
        }else{
            memberParent.setDomainId(domainDao.findFirstByNameAndLevel("总部",3).getId());
        }
        return memberParent;
    }

    public void forgotPwd(String mobile,String password){
         Member member = memberDao.findFirstByMobile(mobile);
         if(null==member){
             throw new ServiceException(1001,"手机号不存在");
         }
         MemberAuth memberAuth = dao.findFirstByMemberId(member.getId());
         memberAuth.setPassword(PasswordUtils.encript(password));
         update(memberAuth.getId(),memberAuth);
    }

    public void updatePwd(String oldPassword,String newPassword){
        Member member = (Member) UserSession.getAuthorize().getUser();
        MemberAuth memberAuth = dao.findFirstByMemberId(member.getId());
        if(PasswordUtils.encript(oldPassword).equals(memberAuth.getPassword())){
            memberAuth.setPassword(PasswordUtils.encript(newPassword));
            update(memberAuth.getId(),memberAuth);
        }else{
            throw new ServiceException(1001,"旧密码错误");
        }
    }

    public void taobaoAuth(String code,String state){
        TaobaoClientUtil taobaoClientUtil = new TaobaoClientUtil();
        String accessToken =  taobaoClientUtil.grabAccessToken(code);
        TbkScPublisherInfoSaveResponse rsp = taobaoClientUtil.grabPublisherInfoSave(accessToken);
        AccessToken token = accessTokenDao.findFirstByTypeAndToken(Constants.ACCESS_TOKEN_TYPE.MEMBER,state);
        Member member = memberDao.findOne(token.getTypeId());
        member.setRelationId(rsp.getData().getRelationId());
        memberService.update(member.getId(),member);
    }

    private AccessToken updateToken(Member member){
        AccessToken accessToken = accessTokenDao.findFirstByTypeAndTypeId(Constants.ACCESS_TOKEN_TYPE.MEMBER,member.getId());
        Date now = new Date();
        if(null!=accessToken){
            accessToken.setToken(PasswordUtils.createRandom(16));
            accessToken.setExpiredTime(DateUtils.goFuture(now,604800));
            accessTokenDao.save(accessToken);
        }else{
            accessToken = new AccessToken();
            accessToken.setType(Constants.ACCESS_TOKEN_TYPE.MEMBER);
            accessToken.setTypeId(member.getId());
            accessToken.setToken(PasswordUtils.createRandom(16));
            accessToken.setExpiredTime(DateUtils.goFuture(now,604800));
            accessTokenDao.save(accessToken);
        }
        return accessToken;
    }

    private void updateLast(MemberAuth memberAuth, HttpServletRequest request){
        memberAuth.setLastLoginIp(IpUtils.getIpAddr(request));
        memberAuth.setLastLoginTime(new Date());
        update(memberAuth.getId(),memberAuth);
    }

    private void addInviteRecord(Long memberId,Long beInvitedId){
        InviteRecord inviteRecord = new InviteRecord();
        inviteRecord.setMemberId(memberId);
        inviteRecord.setBeInvitedId(beInvitedId);
        inviteRecord.setHandle(false);
        inviteRecordService.createOrUpdate(inviteRecord);
    }
}
