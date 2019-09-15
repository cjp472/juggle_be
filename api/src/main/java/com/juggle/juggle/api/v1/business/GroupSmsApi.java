package com.juggle.juggle.api.v1.business;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.business.model.GroupSms;
import com.juggle.juggle.primary.business.service.GroupSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/business/groupSms")
public class GroupSmsApi extends BaseApi<GroupSms,GroupSms,GroupSms> {
    @Autowired
    private GroupSmsService service;

    @Override
    protected ICRUDService<GroupSms> getService() {
        return service;
    }
}
