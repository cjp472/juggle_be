package com.juggle.juggle.api.v1.business;

import com.juggle.juggle.common.data.CaptchaRequest;
import com.juggle.juggle.common.data.VerifyCaptchaRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.business.model.SmsCode;
import com.juggle.juggle.primary.business.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/business/smsCode")
public class SmsCodeApi extends BaseApi<SmsCode,SmsCode,SmsCode> {
    @Autowired
    private SmsCodeService service;

    @Override
    protected ICRUDService<SmsCode> getService() {
        return service;
    }

    @RequestMapping(value = "/getCaptcha",method = {RequestMethod.POST})
    public @ResponseBody Object getCaptcha(@RequestBody CaptchaRequest req){
        service.getCaptcha(req.getMobile());
        return ApiResponse.make();
    }

    @RequestMapping(value = "/verifyCaptcha",method = {RequestMethod.POST})
    public @ResponseBody Object checkCaptcha(@RequestBody VerifyCaptchaRequest req){
        service.verifyCaptcha(req.getMobile(),req.getCaptcha());
        return ApiResponse.make();
    }
}
