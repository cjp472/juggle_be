package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/app/member")
public class MemberApi extends BaseApi<Member,Member,Member> {
    @Autowired
    private MemberService service;

    @Override
    protected ICRUDService<Member> getService() {
        return service;
    }

    @RequestMapping(value = "/{id}/normal",method = {RequestMethod.POST})
    public @ResponseBody Object normal(@PathVariable Long id){
        service.normal(id);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/{id}/forbid",method = {RequestMethod.POST})
    public @ResponseBody Object forbid(@PathVariable Long id){
        service.forbid(id);
        return ApiResponse.make();
    }
}
