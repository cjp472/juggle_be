package com.juggle.juggle.api.v1.platform;

import com.juggle.juggle.api.v1.platform.dto.PayRequest;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.primary.platform.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/platform/aliPay")
public class AliPayApi {
    @Autowired
    private AliPayService service;

    @RequestMapping(value = "/preCreate",method = {RequestMethod.POST})
    public @ResponseBody Object preCreate(@RequestBody PayRequest req){
        String result = service.preCreate(req.getOrderId());
        return ApiResponse.make(result);
    }

    @RequestMapping(value = "/notify/{compose}",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object notify(@PathVariable String compose, HttpServletRequest request){
        Map<String,String[]> map = request.getParameterMap();
        service.notify(compose,map);
        return ApiResponse.make();
    }
}
