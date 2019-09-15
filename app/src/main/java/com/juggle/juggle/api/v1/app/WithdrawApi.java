package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.model.Withdraw;
import com.juggle.juggle.primary.app.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/app/withdraw")
public class WithdrawApi extends BaseApi<Withdraw,Withdraw,Withdraw> {
    @Autowired
    private WithdrawService service;

    @Override
    protected ICRUDService<Withdraw> getService() {
        return service;
    }

    @RequestMapping(value = "/search",method = {RequestMethod.POST})
    public @ResponseBody Object search(@RequestBody @Valid PageSearch pageSearch){
        Member member = (Member) UserSession.getAuthorize().getUser();
        List<ValueFilter> filters = pageSearch.getFilters();
        ValueFilter filter = new ValueFilter("memberId",ValueFilter.OP_EQ,member.getId());
        filters.add(filter);
        pageSearch.setFilters(filters);
        PageResult pageResult = service.searchSimplify(pageSearch);
        return ApiResponse.make(pageResult);
    }
}
