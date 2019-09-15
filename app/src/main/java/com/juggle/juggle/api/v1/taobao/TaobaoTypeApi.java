package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.dto.TaobaoTypeDto;
import com.juggle.juggle.primary.taobao.model.TaobaoType;
import com.juggle.juggle.primary.taobao.service.TaobaoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoType")
public class TaobaoTypeApi extends BaseApi<TaobaoType,TaobaoType,TaobaoType> {
    @Autowired
    private TaobaoTypeService service;

    @Override
    protected ICRUDService<TaobaoType> getService() {
        return service;
    }

    @RequestMapping(value = "/getPrimary",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getPrimary() throws Exception{
        List<TaobaoTypeDto> taobaoTypes = service.getPrimary();
        return ApiResponse.make(taobaoTypes);
    }

    @RequestMapping(value = "/getSecondary/{parentId}",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getSecondary(@PathVariable Long parentId) throws Exception{
        List<TaobaoTypeDto> taobaoTypeDtos = service.getSecondary(parentId);
        return ApiResponse.make(taobaoTypeDtos);
    }

}
