package com.juggle.juggle.api.v1.community;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.community.model.CommunityPublish;
import com.juggle.juggle.primary.community.service.CommunityPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/community/communityPublish")
public class CommunityPublishApi extends BaseApi<CommunityPublish,CommunityPublish,CommunityPublish> {
    @Autowired
    private CommunityPublishService service;

    @Override
    protected ICRUDService<CommunityPublish> getService() {
        return service;
    }
}
