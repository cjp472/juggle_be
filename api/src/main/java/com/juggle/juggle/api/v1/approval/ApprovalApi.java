package com.juggle.juggle.api.v1.approval;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.approval.model.Approval;
import com.juggle.juggle.primary.approval.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/approval/approval")
public class ApprovalApi extends BaseApi<Approval,Approval,Approval> {
    @Autowired
    private ApprovalService service;

    @Override
    protected ICRUDService<Approval> getService() {
        return service;
    }

    @RequestMapping(value = "/{id}/pass",method = {RequestMethod.POST})
    public @ResponseBody Object pass(@PathVariable Long id){
        service.pass(id);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/{id}/reject",method = {RequestMethod.POST})
    public @ResponseBody Object reject(@PathVariable Long id){
        service.reject(id);
        return ApiResponse.make();
    }
}
