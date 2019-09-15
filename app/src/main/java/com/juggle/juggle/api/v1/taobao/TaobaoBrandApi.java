package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.dto.TaobaoBrandDto;
import com.juggle.juggle.primary.taobao.dto.TaobaoBrandItem;
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

    @RequestMapping(value = "readAllEnabled/{categoryId}",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object readAllEnabled(@PathVariable Long categoryId) throws Exception{
        List<TaobaoBrandItem> taobaoBrandItems = service.readAllEnabledByCategoryId(categoryId);
        return ApiResponse.make(taobaoBrandItems);
    }

    @RequestMapping(value = "/{id}/read",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object read(@PathVariable Long id){
        TaobaoBrandDto dto = service.readDetail(id);
        return ApiResponse.make(dto);
    }
}
