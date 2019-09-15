package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoBrand;
import com.juggle.juggle.primary.taobao.service.TaobaoBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoBrand")
public class TaobaoBrandApi extends BaseApi<TaobaoBrand,TaobaoBrand,TaobaoBrand> {
    @Autowired
    private TaobaoBrandService service;

    @Override
    protected ICRUDService<TaobaoBrand> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllEnabled/{typeId}",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object readAllEnabled(@PathVariable Long typeId){
        List<SelectItem> selectDtoList = service.readAllEnabled(typeId);
        return ApiResponse.make(selectDtoList);
    }

    @RequestMapping(value = "/{id}/sync",method = {RequestMethod.POST})
    public @ResponseBody Object sync(@PathVariable Long id){
        service.sync(id);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/{id}/enable",method = {RequestMethod.POST})
    public @ResponseBody
    Object enable(@PathVariable Long id){
        service.enable(id);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/{id}/disable",method = {RequestMethod.POST})
    public @ResponseBody Object disable(@PathVariable Long id){
        service.disable(id);
        return ApiResponse.make();
    }
}
