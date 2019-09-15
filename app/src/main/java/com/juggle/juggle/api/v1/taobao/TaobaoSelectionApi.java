package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoSelection;
import com.juggle.juggle.primary.taobao.service.TaobaoSelectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoSelection")
public class TaobaoSelectionApi extends BaseApi<TaobaoSelection,TaobaoSelection,TaobaoSelection> {
    @Autowired
    private TaobaoSelectionService service;

    @Override
    protected ICRUDService<TaobaoSelection> getService() {
        return service;
    }

    @RequestMapping(value = "/search/{type}",method = {RequestMethod.POST})
    public @ResponseBody
    Object searchByType(@PathVariable String type, @RequestBody PageSearch pageSearch){
        List<ValueFilter> filters = pageSearch.getFilters();
        ValueFilter filter = new ValueFilter("type",ValueFilter.OP_EQ,type);
        filters.add(filter);
        pageSearch.setFilters(filters);
        String sort = pageSearch.getSort();
        if(StringUtils.isEmpty(sort)){
            sort = "id desc";
        }else if(!sort.contains("id")){
            sort = sort + ",id desc";
        }
        pageSearch.setSort(sort);
        PageResult pageResult = service.searchSimplify(pageSearch);
        return ApiResponse.make(pageResult);
    }
}
