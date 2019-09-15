package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoPopular;
import com.juggle.juggle.primary.taobao.service.TaobaoPopularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoPopular")
public class TaobaoPopularApi extends BaseApi<TaobaoPopular,TaobaoPopular,TaobaoPopular> {
    @Autowired
    private TaobaoPopularService service;

    @Override
    protected ICRUDService<TaobaoPopular> getService() {
        return service;
    }

    @RequestMapping(value = "/search/{type}",method = {RequestMethod.POST})
    public @ResponseBody Object searchByType(@PathVariable String type,@RequestBody PageSearch pageSearch){
        List<ValueFilter> filters = pageSearch.getFilters();
        ValueFilter filter = new ValueFilter("type",ValueFilter.OP_EQ,type);
        filters.add(filter);
        pageSearch.setFilters(filters);
        PageResult pageResult = service.searchSimplify(pageSearch);
        return ApiResponse.make(pageResult);
    }
}
