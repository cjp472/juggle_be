package com.juggle.juggle.primary.account.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.account.model.AlipayAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface AlipayAccountDao extends IRepo<AlipayAccount> {
    AlipayAccount findFirstByMemberId(Long memberId);
}
