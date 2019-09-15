package com.juggle.juggle.api.v1.recharge;

import com.juggle.juggle.api.v1.recharge.dto.PhoneBillRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.recharge.model.PhoneBill;
import com.juggle.juggle.primary.recharge.service.PhoneBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/recharge/phoneBill")
public class PhoneBillApi extends BaseApi<PhoneBill,PhoneBill,PhoneBill> {
    @Autowired
    private PhoneBillService service;

    @Override
    protected ICRUDService<PhoneBill> getService() {
        return service;
    }

    @RequestMapping(value = "/createWxBill",method = {RequestMethod.POST})
    public @ResponseBody Object createWxBill(@RequestBody PhoneBillRequest req, HttpServletRequest request){
        Map<String,String> signature = service.createWxBill(req.getMobile(), req.getSkuId(),request);
        return ApiResponse.make(signature);
    }

    @RequestMapping(value = "/createAliBill",method = {RequestMethod.POST})
    public @ResponseBody Object createAliBill(@RequestBody PhoneBillRequest req){
        String signature = service.createAliBill(req.getMobile(),req.getSkuId());
        return ApiResponse.make(signature);
    }
}
