package com.juggle.juggle.primary.account.service;

import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.account.dao.BankAccountDao;
import com.juggle.juggle.primary.account.dto.BankAccountDto;
import com.juggle.juggle.primary.account.model.BankAccount;
import com.juggle.juggle.primary.app.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService extends BaseCRUDService<BankAccount> {
    @Autowired
    private BankAccountDao dao;

    @Override
    protected IRepo<BankAccount> getRepo() {
        return dao;
    }

    public List<BankAccountDto> readAllMy() throws Exception{
        Member member = (Member) UserSession.getAuthorize().getUser();
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();
        List<BankAccount> bankAccounts = dao.findAllByMemberId(member.getId());
        for (BankAccount bankAccount : bankAccounts) {
            BankAccountDto bankAccountDto = new BankAccountDto();
            PropertyCopyUtil.getInstance().copyProperties(bankAccountDto,bankAccount);
            bankAccountDtos.add(bankAccountDto);
        }
        return bankAccountDtos;
    }
}
