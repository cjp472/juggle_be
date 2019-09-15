package com.juggle.juggle.primary.app.service;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.dao.MemberCertDao;
import com.juggle.juggle.primary.app.dto.MemberCertProcess;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.model.MemberCert;
import com.juggle.juggle.primary.approval.dao.ApprovalDao;
import com.juggle.juggle.primary.approval.model.Approval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCertService extends BaseCRUDService<MemberCert> {
    @Autowired
    private MemberCertDao dao;

    @Autowired
    private ApprovalDao approvalDao;

    @Override
    protected IRepo<MemberCert> getRepo() {
        return dao;
    }

    public MemberCertProcess checkCert(){
        Member member = (Member) UserSession.getAuthorize().getUser();
        MemberCertProcess memberCertProcess = new MemberCertProcess();
        memberCertProcess.setCertified(member.isCertified());
        Approval approval = approvalDao.findFirstByMemberIdAndProcessInstanceTypeOrderByIdDesc(member.getId(), Constants.APPROVAL_PROCESS_INSTANCE_TYPE.CERT);
        if(null!=approval){
            memberCertProcess.setProcessStatus(approval.getProcessStatus());
        }
        return memberCertProcess;
    }

    public MemberCert getCert(){
        MemberCert memberCert = dao.findFirstByMemberId(Long.valueOf(UserSession.getAuthorize().getUserId().toString()));
        MemberCert dest = new MemberCert();
        try {
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dest,memberCert,"updatedTime","updatedBy","createdTime","createdBy");
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return dest;
    }
}
