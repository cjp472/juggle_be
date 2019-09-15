package com.juggle.juggle.api.v1.auth;

import com.juggle.juggle.api.v1.auth.dto.CheckPwdDto;
import com.juggle.juggle.common.annotation.OperLog;
import com.juggle.juggle.common.data.VerifyCaptchaRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.api.v1.auth.dto.LoginDto;
import com.juggle.juggle.api.v1.auth.dto.UpdatePwdDto;
import com.juggle.juggle.primary.auth.dto.UserProfile;
import com.juggle.juggle.primary.auth.model.UserAuth;
import com.juggle.juggle.primary.auth.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthApi extends BaseApi<UserAuth,UserAuth,UserAuth> {
    @Autowired
    private UserAuthService service;

    @Override
    protected ICRUDService<UserAuth> getService() {
        return service;
    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    public @ResponseBody Object login(@RequestBody LoginDto req, HttpServletRequest request){
        UserProfile userProfile = service.login(req.getName(),req.getPassword(),request);
        return ApiResponse.make(userProfile);
    }

    @RequestMapping(value = "/loginByMobile",method = {RequestMethod.POST})
    public @ResponseBody Object loginByMobile(@RequestBody VerifyCaptchaRequest req,HttpServletRequest request){
        UserProfile userProfile = service.loginByMobile(req.getMobile(),req.getCaptcha(),request);
        return ApiResponse.make(userProfile);
    }

    @RequestMapping(value = "/checkPwd",method = {RequestMethod.POST})
    public @ResponseBody Object checkPwd(@RequestBody CheckPwdDto req){
        service.checkPwd(req.getPassword());
        return ApiResponse.make();
    }

    @OperLog(name = "修改密码")
    @RequestMapping(value = "/updatePwd",method = {RequestMethod.POST})
    public @ResponseBody Object updatePwd(@RequestBody UpdatePwdDto req){
        service.updatePwd(req.getOldPassword(),req.getNewPassword());
        return ApiResponse.make();
    }
}
