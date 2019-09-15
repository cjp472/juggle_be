package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.common.data.DateRangeRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.primary.app.dto.LastRewardCensus;
import com.juggle.juggle.primary.app.dto.PresentRewardCensus;
import com.juggle.juggle.primary.app.dto.RewardCensus;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.model.Reward;
import com.juggle.juggle.primary.app.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/app/reward")
public class RewardApi extends BaseApi<Reward,Reward,Reward> {
    @Autowired
    private RewardService service;

    @Override
    public RewardService getService() {
        return service;
    }

    public void setService(RewardService service) {
        this.service = service;
    }

    @RequestMapping(value = "/getCensus",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getCensus(){
        RewardCensus census = service.getCensus();
        return ApiResponse.make(census);
    }

    @RequestMapping(value = "/getLastCensus",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getLastCensus(){
        LastRewardCensus census = service.getLastCensus();
        return ApiResponse.make(census);
    }

    @RequestMapping(value = "/getPresentCensus",method = {RequestMethod.POST})
    public @ResponseBody Object getPresentCensus(@RequestBody DateRangeRequest req){
        PresentRewardCensus census = service.getPresentCensus(req.getStartTime(),req.getEndTime());
        return ApiResponse.make(census);
    }

    @RequestMapping(value = "/search",method = {RequestMethod.POST})
    public @ResponseBody Object search(@RequestBody @Valid PageSearch pageSearch){
        List<ValueFilter> filters = pageSearch.getFilters();
        Member member = (Member) UserSession.getAuthorize().getUser();
        ValueFilter filter = new ValueFilter("memberId",ValueFilter.OP_EQ,member.getId());
        filters.add(filter);
        pageSearch.setFilters(filters);
        PageResult pageResult = service.searchSimplify(pageSearch);
        return ApiResponse.make(pageResult);
    }
}
