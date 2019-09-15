package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.dto.MemberCertProcess;
import com.juggle.juggle.primary.app.model.MemberCert;
import com.juggle.juggle.primary.app.service.MemberCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/app/memberCert")
public class MemberCertApi extends BaseApi<MemberCert,MemberCert,MemberCert> {
    @Autowired
    private MemberCertService service;

    @Override
    protected ICRUDService<MemberCert> getService() {
        return service;
    }

    @RequestMapping(value = "/checkCert",method = {RequestMethod.POST})
    public @ResponseBody Object checkCert(){
        MemberCertProcess result = service.checkCert();
        return ApiResponse.make(result);
    }

    @RequestMapping(value = "/getCert",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getCert(){
        MemberCert memberCert = service.getCert();
        return ApiResponse.make(memberCert);
    }
}
