package com.juggle.juggle.api.v1.system;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.system.model.Position;
import com.juggle.juggle.primary.system.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/system/position")
public class PositionApi extends BaseApi<Position,Position,Position> {
    @Autowired
    private PositionService service;

    @Override
    protected ICRUDService<Position> getService() {
        return service;
    }


    @RequestMapping(value = "/readAllEnabled",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object readAllEnabled(){
        List<SelectItem> selectDtoList = service.readAllEnabled();
        return ApiResponse.make(selectDtoList);
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
