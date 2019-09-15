package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.model.Reward;
import com.juggle.juggle.primary.app.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/app/reward")
public class RewardApi extends BaseApi<Reward,Reward,Reward> {
    @Autowired
    private RewardService service;

    @Override
    protected ICRUDService<Reward> getService() {
        return service;
    }
}
