package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.common.data.TaobaoDetailRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.dto.TaobaoAlbum;
import com.juggle.juggle.primary.taobao.dto.TaobaoDetaily;
import com.juggle.juggle.primary.taobao.model.TaobaoExtra;
import com.juggle.juggle.primary.taobao.service.TaobaoExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoExtra")
public class TaobaoExtraApi extends BaseApi<TaobaoExtra,TaobaoExtra,TaobaoExtra> {
    @Autowired
    private TaobaoExtraService service;

    @Override
    protected ICRUDService<TaobaoExtra> getService() {
        return service;
    }

    @RequestMapping(value = "/readDetail",method = {RequestMethod.POST})
    public @ResponseBody Object readDetail(@RequestBody TaobaoDetailRequest req){
        TaobaoDetaily detaily = service.readDetail(req.getId(), req.getSub());
        return ApiResponse.make(detaily);
    }

    @RequestMapping(value = "/readAlbum",method = {RequestMethod.POST})
    public @ResponseBody Object readAlbum(@RequestBody TaobaoDetailRequest req){
        TaobaoAlbum album = service.readAlbum(req.getId(),req.getSub());
        return ApiResponse.make(album);
    }

    @RequestMapping(value = "/tpwdCreate",method = {RequestMethod.POST})
    public @ResponseBody Object tpwdCreate(@RequestBody TaobaoDetailRequest req){
        Map<String,String> map  = service.tpwdCreate(req.getId(),req.getSub());
        return ApiResponse.make(map);
    }
}
