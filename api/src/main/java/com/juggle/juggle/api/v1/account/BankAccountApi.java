package com.juggle.juggle.api.v1.account;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.account.model.BankAccount;
import com.juggle.juggle.primary.account.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/account/bankAccount")
public class BankAccountApi extends BaseApi<BankAccount,BankAccount,BankAccount> {
    @Autowired
    private BankAccountService service;

    @Override
    protected ICRUDService<BankAccount> getService() {
        return service;
    }
}
