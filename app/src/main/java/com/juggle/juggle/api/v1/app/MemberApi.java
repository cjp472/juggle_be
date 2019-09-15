package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.common.data.StringRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.dto.MemberDto;
import com.juggle.juggle.primary.app.dto.MemberModifyDto;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.service.MemberService;
import com.juggle.juggle.primary.auth.service.MemberAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/app/member")
public class MemberApi extends BaseApi<Member,Member,Member> {
    @Autowired
    private MemberService service;

    @Autowired
    private MemberAuthService memberAuthService;

    @Override
    protected ICRUDService<Member> getService() {
        return service;
    }

    @RequestMapping(value = "/profile",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object profile(){
        MemberDto memberDto = service.profile();
        return ApiResponse.make(memberDto);
    }

    @RequestMapping(value = "/modify",method = {RequestMethod.POST})
    public @ResponseBody Object modify(@RequestBody MemberModifyDto req){
        MemberDto memberDto = service.modify(req);
        return ApiResponse.make(memberDto);
    }

    @RequestMapping(value = "/setPayPassword",method = {RequestMethod.POST})
    public @ResponseBody Object setPayPassword(@RequestBody StringRequest req){
        memberAuthService.setPayPassword(req.getValue());
        return ApiResponse.make();
    }

    @RequestMapping(value = "/checkPayPassword",method = {RequestMethod.POST})
    public @ResponseBody Object checkPayPassword(@RequestBody StringRequest req){
        boolean result = memberAuthService.checkPayPassword(req.getValue());
        return ApiResponse.make(result);
    }

    @RequestMapping(value = "/getTeam/{grade}",method = {RequestMethod.POST})
    public @ResponseBody Object getTeam(@PathVariable String grade, @RequestBody PageSearch pageSearch){
        PageResult pageResult = service.getTeam(pageSearch,grade);
        return ApiResponse.make(pageResult);
    }
}
