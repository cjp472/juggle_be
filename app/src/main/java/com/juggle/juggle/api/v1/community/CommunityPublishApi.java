package com.juggle.juggle.api.v1.community;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.community.model.CommunityPublish;
import com.juggle.juggle.primary.community.service.CommunityPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/community/communityPublish")
public class CommunityPublishApi extends BaseApi<CommunityPublish,CommunityPublish,CommunityPublish> {
    @Autowired
    private CommunityPublishService service;

    @Override
    protected ICRUDService<CommunityPublish> getService() {
        return service;
    }

    @RequestMapping(value = "/search",method = {RequestMethod.POST})
    public @ResponseBody Object search(@RequestBody PageSearch pageSearch){
        PageResult pageResult = service.searchPublish(pageSearch);
        return ApiResponse.make(pageResult);
    }
}
