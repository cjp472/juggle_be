package com.juggle.juggle.api.v1.auth;

import com.juggle.juggle.api.v1.auth.dto.*;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.auth.dto.MemberParent;
import com.juggle.juggle.primary.auth.dto.MemberProfile;
import com.juggle.juggle.primary.auth.model.MemberAuth;
import com.juggle.juggle.primary.auth.service.MemberAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthApi extends BaseApi<MemberAuth,MemberAuth,MemberAuth> {
    @Autowired
    private MemberAuthService service;

    @Override
    protected ICRUDService<MemberAuth> getService() {
        return service;
    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    public @ResponseBody Object login(@RequestBody LoginDto req, HttpServletRequest request){
        MemberProfile memberProfile = service.login(req.getMobile(),req.getPassword(),request);
        return ApiResponse.make(memberProfile);
    }

    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    public @ResponseBody Object register(@RequestBody RegisterDto req, HttpServletRequest request){
        MemberProfile memberProfile = service.register(req.getInviteCode(),req.getMobile(),req.getPassword(),req.getDomainId(),request);
        return ApiResponse.make(memberProfile);
    }

    @RequestMapping(value = "/inviteRegister",method = {RequestMethod.POST})
    public @ResponseBody Object inviteRegister(@RequestBody InviteRegisterDto req,HttpServletRequest request){
        Member member = service.inviteRegister(req.getInviteCode(),req.getMobile(),req.getPassword(),request);
        return ApiResponse.make(member);
    }

    @RequestMapping(value = "/parent",method = {RequestMethod.POST})
    public @ResponseBody Object parent(@RequestBody ParentDto req){
        MemberParent memberParent = service.parent(req.getInviteCode());
        return ApiResponse.make(memberParent);
    }

    @RequestMapping(value = "/forgotPwd",method = {RequestMethod.POST})
    public @ResponseBody Object forgotPwd(@RequestBody ForgotPwdDto req){
        service.forgotPwd(req.getMobile(),req.getPassword());
        return ApiResponse.make();
    }

    @RequestMapping(value = "/updatePwd",method = {RequestMethod.POST})
    public @ResponseBody Object updatePwd(@RequestBody UpdatePwdDto req){
        service.updatePwd(req.getOldPassword(),req.getNewPassword());
        return ApiResponse.make();
    }

    @RequestMapping(value = "/taobao",method = {RequestMethod.GET})
    public @ResponseBody Object taobao(@RequestParam("code") String code,@RequestParam("state") String state){
        service.taobaoAuth(code,state);
        return ApiResponse.make();
    }
}
