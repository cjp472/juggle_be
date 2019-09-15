package com.juggle.juggle.api.v1.community;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.community.dto.CommunityDto;
import com.juggle.juggle.primary.community.model.Community;
import com.juggle.juggle.primary.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/community/community")
public class CommunityApi extends BaseApi<Community,Community,Community> {
    @Autowired
    private CommunityService service;

    @Override
    protected ICRUDService<Community> getService() {
        return service;
    }

    @RequestMapping(value = "/readAll",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object readAllEnabled() throws Exception{
        List<CommunityDto> communities = service.readAllCommunity();
        return ApiResponse.make(communities);
    }
}
