package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.model.Withdraw;
import com.juggle.juggle.primary.app.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/app/withdraw")
public class WithdrawApi extends BaseApi<Withdraw,Withdraw,Withdraw> {
    @Autowired
    private WithdrawService service;

    @Override
    protected ICRUDService<Withdraw> getService() {
        return service;
    }
}
