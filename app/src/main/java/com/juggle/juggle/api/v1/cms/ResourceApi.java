package com.juggle.juggle.api.v1.cms;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.cms.dto.ResourceDto;
import com.juggle.juggle.primary.cms.model.Resource;
import com.juggle.juggle.primary.cms.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/cms/resource")
public class ResourceApi extends BaseApi<Resource,Resource,Resource> {
    @Autowired
    private ResourceService service;

    @Override
    protected ICRUDService<Resource> getService() {
        return service;
    }

    @RequestMapping(value = "/startPictures",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object startPictures(){
        List<ResourceDto> resourceDtos = service.getResources("CMS_START_PICTURES");
        return ApiResponse.make(resourceDtos);
    }

    @RequestMapping(value = "/homeIcons",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object homeIcons(){
        List<ResourceDto> resourceDtos = service.getResources("CMS_HOME_ICONS");
        return ApiResponse.make(resourceDtos);
    }

    @RequestMapping(value = "/homeActivities",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object homeActivities(){
        List<ResourceDto> resourceDtos = service.getResources("CMS_HOME_ACTIVITIES");
        return ApiResponse.make(resourceDtos);
    }

    @RequestMapping(value = "/homeModules",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object homeModules(){
        List<ResourceDto> resourceDtos = service.getResources("CMS_HOME_MODULES");
        return ApiResponse.make(resourceDtos);
    }
}
