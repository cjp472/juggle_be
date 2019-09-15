package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoFavourable;
import com.juggle.juggle.primary.taobao.service.TaobaoFavourableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoFavourable")
public class TaobaoFavourableApi extends BaseApi<TaobaoFavourable,TaobaoFavourable,TaobaoFavourable> {
    @Autowired
    private TaobaoFavourableService service;

    @Override
    protected ICRUDService<TaobaoFavourable> getService() {
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
