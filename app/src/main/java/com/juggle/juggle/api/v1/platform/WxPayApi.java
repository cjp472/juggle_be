package com.juggle.juggle.api.v1.platform;

import com.juggle.juggle.api.v1.platform.dto.PayRequest;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.primary.platform.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/platform/wxPay")
public class WxPayApi {
    @Autowired
    private WxPayService service;

    @RequestMapping(value="/unifiedOrder",method = {RequestMethod.POST})
    public @ResponseBody Object unifiedOrder(@RequestBody PayRequest req, HttpServletRequest request){
        Map<String,String> map = service.unifiedOrder(req.getOrderId(),request);
        return ApiResponse.make(map);
    }

    @RequestMapping(value = "/notify/{compose}",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object notify(@PathVariable String compose,HttpServletRequest request) throws Exception{
        String inputLine;
        String notifyXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notifyXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            throw e;
        }
        service.notify(compose,notifyXml);
        return ApiResponse.make();
    }
}
