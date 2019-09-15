package com.juggle.juggle.primary.account.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.account.model.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountDao extends IRepo<BankAccount> {
    List<BankAccount> findAllByMemberId(Long memberId);
}
