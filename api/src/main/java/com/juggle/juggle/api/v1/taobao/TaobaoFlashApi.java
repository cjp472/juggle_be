package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoFlash;
import com.juggle.juggle.primary.taobao.service.TaobaoFlashService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoFlash")
public class TaobaoFlashApi extends BaseApi<TaobaoFlash,TaobaoFlash,TaobaoFlash> {
    @Autowired
    private TaobaoFlashService service;

    @Override
    protected ICRUDService<TaobaoFlash> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllRush",method = {RequestMethod.POST})
    public @ResponseBody Object readAllRush(){
        List<SelectItem> selectItems = service.readAllRush();
        return ApiResponse.make(selectItems);
    }

    @RequestMapping(value = "/search/{type}",method = {RequestMethod.POST})
    public @ResponseBody
    Object search(@PathVariable String type,@RequestBody @Valid PageSearch pageRequest){
        List<ValueFilter> valueFilters = pageRequest.getFilters();
        String sort = pageRequest.getSort();
        if(StringUtils.isEmpty(sort)||!sort.contains("id")){
            sort = "id desc";
            pageRequest.setSort(sort);
        }
        if(type.equals("TODAY")){
            ValueFilter valueFilter = new ValueFilter("startTime","<", DateUtils.start(DateUtils.next(new Date())).getTime());
            valueFilters.add(valueFilter);
        }else{
            ValueFilter valueFilter = new ValueFilter("startTime",">=",DateUtils.start(DateUtils.next(new Date())).getTime());
            valueFilters.add(valueFilter);
        }
        pageRequest.setFilters(valueFilters);
        return ApiResponse.make(this.getService().search(pageRequest));
    }

    @RequestMapping(value = "/{id}/switchTop",method = {RequestMethod.POST})
    public @ResponseBody Object switchTop(@PathVariable Long id){
        service.switchTop(id);
        return ApiResponse.make();
    }
}
