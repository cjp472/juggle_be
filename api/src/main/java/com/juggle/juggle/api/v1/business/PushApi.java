package com.juggle.juggle.api.v1.business;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.business.model.Push;
import com.juggle.juggle.primary.business.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/business/push")
public class PushApi extends BaseApi<Push,Push,Push> {
    @Autowired
    private PushService service;

    @Override
    protected ICRUDService<Push> getService() {
        return service;
    }

    @RequestMapping(value = "/{id}/push",method = {RequestMethod.POST})
    public @ResponseBody Object push(@PathVariable Long id){
        Push push = service.push(id);
        return ApiResponse.make(push);
    }
}
