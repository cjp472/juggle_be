package com.juggle.juggle.primary.account.service;

import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.account.dao.MemberAccountDao;
import com.juggle.juggle.primary.account.dto.MemberAccountCensus;
import com.juggle.juggle.primary.account.model.MemberAccount;
import com.juggle.juggle.primary.app.dao.RewardDao;
import com.juggle.juggle.primary.app.dao.WithdrawDao;
import com.juggle.juggle.primary.app.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MemberAccountService extends BaseCRUDService<MemberAccount> {
    @Autowired
    private MemberAccountDao dao;

    @Autowired
    private RewardDao rewardDao;

    @Autowired
    private WithdrawDao withdrawDao;

    @Override
    protected IRepo<MemberAccount> getRepo() {
        return dao;
    }

    public MemberAccountCensus getCensus(){
        Member member = (Member) UserSession.getAuthorize().getUser();
        MemberAccountCensus census = new MemberAccountCensus();
        MemberAccount memberAccount = dao.findFirstByMemberId(member.getId());
        census.setTotalReward(defaultZero(rewardDao.sumAllRewardByMemberId(member.getId())));
        census.setTotalWithdraw(defaultZero(withdrawDao.sumAllPaidByMemberId(member.getId())));
        census.setTotalPend(defaultZero(rewardDao.sumAllPendByMemberId(member.getId())));
        census.setAmount(memberAccount.getAmount());
        return census;
    }

    private BigDecimal defaultZero(BigDecimal value){
        if(null!=value){
            return value;
        }
        return BigDecimal.ZERO;
    }
}
