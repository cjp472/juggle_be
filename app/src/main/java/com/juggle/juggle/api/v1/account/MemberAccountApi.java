package com.juggle.juggle.api.v1.account;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.account.dto.MemberAccountCensus;
import com.juggle.juggle.primary.account.model.MemberAccount;
import com.juggle.juggle.primary.account.service.MemberAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/account/memberAccount")
public class MemberAccountApi extends BaseApi<MemberAccount,MemberAccount,MemberAccount> {
    @Autowired
    private MemberAccountService service;

    @Override
    protected ICRUDService<MemberAccount> getService() {
        return service;
    }

    @RequestMapping(value = "/getCensus",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getCensus(){
        MemberAccountCensus census = service.getCensus();
        return ApiResponse.make(census);
    }
}
