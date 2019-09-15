package com.juggle.juggle.api.v1.cms;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.cms.model.Resource;
import com.juggle.juggle.primary.cms.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/cms/resource")
public class ResourceApi extends BaseApi<Resource,Resource,Resource> {
    @Autowired
    private ResourceService service;

    @Override
    protected ICRUDService<Resource> getService() {
        return service;
    }
}
