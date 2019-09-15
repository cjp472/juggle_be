package com.juggle.juggle.primary.approval.service;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.account.dao.AlipayAccountDao;
import com.juggle.juggle.primary.account.dao.MemberAccountDao;
import com.juggle.juggle.primary.account.model.AlipayAccount;
import com.juggle.juggle.primary.account.model.MemberAccount;
import com.juggle.juggle.primary.app.dao.MemberCertDao;
import com.juggle.juggle.primary.app.dao.MemberDao;
import com.juggle.juggle.primary.app.dao.WithdrawDao;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.model.MemberCert;
import com.juggle.juggle.primary.app.model.Withdraw;
import com.juggle.juggle.primary.app.service.MemberCertService;
import com.juggle.juggle.primary.app.service.WithdrawService;
import com.juggle.juggle.primary.approval.dao.ApprovalDao;
import com.juggle.juggle.primary.approval.dto.WithdrawApply;
import com.juggle.juggle.primary.approval.model.Approval;
import com.juggle.juggle.primary.setting.dao.DictionaryDao;
import com.juggle.juggle.primary.setting.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApprovalService extends BaseCRUDService<Approval> {
    @Autowired
    private ApprovalDao dao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberCertService memberCertService;

    @Autowired
    private MemberCertDao memberCertDao;

    @Autowired
    private MemberAccountDao memberAccountDao;

    @Autowired
    private AlipayAccountDao alipayAccountDao;

    @Autowired
    private WithdrawDao withdrawDao;

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    protected IRepo<Approval> getRepo() {
        return dao;
    }

    public void certApply(MemberCert memberCert){
        Member member = (Member)UserSession.getAuthorize().getUser();
        MemberCert existCert = memberCertDao.findFirstByMemberId(member.getId());
        if(existCert!=null){
            memberCert.setMemberId(member.getId());
            memberCert = memberCertService.update(existCert.getId(),memberCert);
        }else{
            memberCert.setMemberId(member.getId());
            memberCert = memberCertService.create(memberCert);
        }
        Approval approval = new Approval();
        approval.setMemberId(member.getId());
        approval.setProcessInstanceType(Constants.APPROVAL_PROCESS_INSTANCE_TYPE.CERT);
        approval.setProcessInstanceId(memberCert.getId());
        approval.setProcessStatus(Constants.APPROVAL_PROCESS_STATUS.OPEN);
        Dictionary dictionary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"APPROVAL_CERTIFICATE_AUTO");
        approval = create(approval);
        if(dictionary.getDictValue().equals("1")){
            complete(approval.getId(),Constants.APPROVAL_PROCESS_STATUS.PASS);
        }
    }

    public void withdrawApply(WithdrawApply apply){
        Member member = (Member)UserSession.getAuthorize().getUser();
        MemberAccount memberAccount = memberAccountDao.findFirstByMemberId(member.getId());
        if(memberAccount.getAmount().compareTo(apply.getAmount())>=0){
            Withdraw withdraw = new Withdraw();
            withdraw.setMemberId(member.getId());
            withdraw.setAccountType(apply.getAccountType());
            withdraw.setAccountId(apply.getAccountId());
            if(apply.getAccountType().equals(Constants.WITHDRAW_ACCOUNT_TYPE.ALIPAY)&&null==apply.getAccountId()){
                AlipayAccount alipayAccount = alipayAccountDao.findFirstByMemberId(member.getId());
                withdraw.setAccountId(alipayAccount.getId());
            }
            withdraw.setAmount(apply.getAmount());
            withdraw.setStatus(Constants.WITHDRAW_STATUS.PROCESS);
            withdraw = withdrawService.create(withdraw);
            memberAccount.setAmount(memberAccount.getAmount().subtract(apply.getAmount()));
            memberAccountDao.save(memberAccount);
            Approval approval = new Approval();
            approval.setMemberId(member.getId());
            approval.setProcessInstanceType(Constants.APPROVAL_PROCESS_INSTANCE_TYPE.WITHDRAW);
            approval.setProcessInstanceId(withdraw.getId());
            approval.setProcessStatus(Constants.APPROVAL_PROCESS_STATUS.OPEN);
            approval = create(approval);
            Dictionary dictionary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"APPROVAL_WITHDRAW_AUTO");
            if(dictionary.getDictValue().equals("1")){
                complete(approval.getId(),Constants.APPROVAL_PROCESS_STATUS.PASS);
            }
        }else{
            throw new ServiceException(1001,"会员账号余额不足");
        }
    }

    public void pass(Long id){
        complete(id,Constants.APPROVAL_PROCESS_STATUS.PASS);
    }

    public void reject(Long id){
        complete(id,Constants.APPROVAL_PROCESS_STATUS.REJECT);
    }

    public void complete(Long id,String processStatus){
        Approval approval = dao.findOne(id);
        if(approval.getProcessInstanceType().equals(Constants.APPROVAL_PROCESS_INSTANCE_TYPE.CERT)){
            MemberCert memberCert = memberCertDao.findOne(approval.getProcessInstanceId());
            if(processStatus.equals(Constants.APPROVAL_PROCESS_STATUS.PASS)){
                Member member = memberDao.findOne(memberCert.getMemberId());
                member.setCertified(true);
                memberDao.save(member);
            }
        }else if(approval.getProcessInstanceType().equals(Constants.APPROVAL_PROCESS_INSTANCE_TYPE.WITHDRAW)){
            Withdraw withdraw = withdrawDao.findOne(approval.getProcessInstanceId());
            if(processStatus.equals(Constants.APPROVAL_PROCESS_STATUS.PASS)){
                if(withdraw.getAccountType().equals(Constants.WITHDRAW_ACCOUNT_TYPE.ALIPAY)){
                    withdraw.setStatus(Constants.WITHDRAW_STATUS.PEND);
                }else{
                    withdraw.setStatus(Constants.WITHDRAW_STATUS.PAID);
                }
            }else{
                withdraw.setStatus(Constants.WITHDRAW_STATUS.INVALID);
                MemberAccount memberAccount = memberAccountDao.findFirstByMemberId(withdraw.getMemberId());
                memberAccount.setAmount(memberAccount.getAmount().add(withdraw.getAmount()));
                memberAccountDao.save(memberAccount);
            }
            withdrawDao.save(withdraw);
        }
        approval.setProcessStatus(processStatus);
        approval.setApprovalBy((Long)UserSession.getAuthorize().getUserId());
        approval.setApprovalTime(new Date());
        update(id,approval);
    }
}
