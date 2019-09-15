package com.juggle.juggle.api.v1.setting;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.setting.dto.DomainRelation;
import com.juggle.juggle.primary.setting.model.Domain;
import com.juggle.juggle.primary.setting.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/setting/domain")
public class DomainApi extends BaseApi<Domain,Domain,Domain> {
    @Autowired
    private DomainService service;

    @Override
    protected ICRUDService<Domain> getService() {
        return service;
    }

    @RequestMapping(value = "/getRelation",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getRelation() throws Exception{
        List<DomainRelation> domains = service.getRelation();
        return ApiResponse.make(domains);
    }
}
