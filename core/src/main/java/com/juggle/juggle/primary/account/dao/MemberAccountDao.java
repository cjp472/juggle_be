package com.juggle.juggle.primary.account.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.account.model.MemberAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAccountDao extends IRepo<MemberAccount> {
    MemberAccount findFirstByMemberId(Long memberId);
}
