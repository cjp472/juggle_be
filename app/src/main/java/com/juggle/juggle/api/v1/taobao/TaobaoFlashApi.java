package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.dto.TaobaoFlashRush;
import com.juggle.juggle.primary.taobao.model.TaobaoFlash;
import com.juggle.juggle.primary.taobao.service.TaobaoFlashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoFlash")
public class TaobaoFlashApi extends BaseApi<TaobaoFlash, TaobaoFlash,TaobaoFlash> {
    @Autowired
    private TaobaoFlashService service;

    @Override
    protected ICRUDService<TaobaoFlash> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllRush",method = {RequestMethod.POST})
    public @ResponseBody
    Object readAllRush(){
        List<TaobaoFlashRush> flashRushes = service.readAllTodayRush();
        return ApiResponse.make(flashRushes);
    }

    @RequestMapping(value = "/search",method = {RequestMethod.POST})
    public @ResponseBody
    Object search(@RequestBody @Valid PageSearch pageSearch){
        PageResult pageResult = service.searchRushSimplify(pageSearch);
        return ApiResponse.make(pageResult);
    }
}
