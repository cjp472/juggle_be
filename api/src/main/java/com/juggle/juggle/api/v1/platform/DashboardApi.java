package com.juggle.juggle.api.v1.platform;

import com.juggle.juggle.api.v1.platform.dto.KeywordRequest;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.primary.platform.data.DashboardCensus;
import com.juggle.juggle.primary.platform.data.GlobalSearch;
import com.juggle.juggle.primary.platform.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/platform/dashboard")
public class DashboardApi {
    @Autowired
    private DashboardService service;

    @RequestMapping(value = "/getCensus",method = {RequestMethod.POST})
    public @ResponseBody Object getCensus(){
        DashboardCensus census = service.getCensus();
        return ApiResponse.make(census);
    }

    @RequestMapping(value = "/search",method = {RequestMethod.POST})
    public @ResponseBody Object search(@RequestBody KeywordRequest request){
        GlobalSearch globalSearch = service.globalSearch(request.getKeyword());
        return ApiResponse.make(globalSearch);
    }
}
