package com.juggle.juggle.api.v1.system;

import com.juggle.juggle.common.annotation.OperLog;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.system.model.User;
import com.juggle.juggle.primary.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/system/user")
public class UserApi extends BaseApi<User,User,User> {
    @Autowired
    private UserService service;

    @Override
    protected ICRUDService<User> getService() {
        return service;
    }

    @OperLog(name = "禁用用户")
    @RequestMapping(value = "/{id}/normal",method = {RequestMethod.POST})
    public @ResponseBody Object normal(@PathVariable Long id){
        service.normal(id);
        return ApiResponse.make();
    }

    @OperLog(name = "启用用户")
    @RequestMapping(value = "/{id}/forbid",method = {RequestMethod.POST})
    public @ResponseBody Object forbid(@PathVariable Long id){
        service.forbid(id);
        return ApiResponse.make();
    }
}
