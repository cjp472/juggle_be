package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.dto.DeliveryAddressDto;
import com.juggle.juggle.primary.app.model.DeliveryAddress;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/app/deliveryAddress")
public class DeliveryAddressApi extends BaseApi<DeliveryAddress,DeliveryAddress,DeliveryAddress> {
    @Autowired
    private DeliveryAddressService service;

    @Override
    protected ICRUDService<DeliveryAddress> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllAddress",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object readAll() throws Exception{
        List<DeliveryAddressDto> addresses = service.readAllAddress();
        return ApiResponse.make(addresses);
    }

    @RequestMapping(value = "/create",method = {RequestMethod.POST})
    public @ResponseBody Object create(@RequestBody @Valid DeliveryAddress obj){
        Member member = (Member) UserSession.getAuthorize().getUser();
        obj.setMemberId(member.getId());
        return ApiResponse.make(this.getService().create(obj));
    }
}
