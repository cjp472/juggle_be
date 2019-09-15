package com.juggle.juggle.api.v1.account;

import com.juggle.juggle.api.v1.account.dto.AlipayAccountBind;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.account.model.AlipayAccount;
import com.juggle.juggle.primary.account.service.AlipayAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/account/alipayAccount")
public class AlipayAccountApi extends BaseApi<AlipayAccount,AlipayAccount,AlipayAccount> {
    @Autowired
    private AlipayAccountService service;

    @Override
    protected ICRUDService<AlipayAccount> getService() {
        return service;
    }

    @RequestMapping(value = "/read",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object read(){
        AlipayAccount alipayAccount = service.readAccount();
        return ApiResponse.make(alipayAccount);
    }

    @RequestMapping(value = "/bind",method = {RequestMethod.POST})
    public @ResponseBody Object bind(@RequestBody AlipayAccountBind req){
        AlipayAccount alipayAccount = service.bindAccount(req.getName(),req.getAccount());
        return ApiResponse.make(alipayAccount);
    }

}
