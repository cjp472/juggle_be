package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.taobao.model.TaobaoOrder;
import com.juggle.juggle.primary.taobao.service.TaobaoOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoOrder")
public class TaobaoOrderApi extends BaseApi<TaobaoOrder,TaobaoOrder,TaobaoOrder> {
    @Autowired
    private TaobaoOrderService service;

    @Override
    protected ICRUDService<TaobaoOrder> getService() {
        return service;
    }

    public @ResponseBody
    Object search(@RequestBody @Valid PageSearch pageRequest){
        Member member = (Member) UserSession.getAuthorize().getUser();
        String sort = pageRequest.getSort();
        if(StringUtils.isEmpty(sort)){
            sort = "tkCreateTime desc";
        }else if(!sort.contains("tkCreateTime")){
            sort = sort + ",tkCreateTime desc";
        }
        List<ValueFilter> filters = pageRequest.getFilters();
        ValueFilter filter = new ValueFilter("relationId",ValueFilter.OP_EQ,member.getRelationId());
        filters.add(filter);
        pageRequest.setFilters(filters);
        pageRequest.setSort(sort);
        return ApiResponse.make(this.getService().search(pageRequest));
    }
}
