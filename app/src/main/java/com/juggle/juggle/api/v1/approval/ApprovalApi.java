package com.juggle.juggle.api.v1.approval;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.model.MemberCert;
import com.juggle.juggle.primary.approval.dto.WithdrawApply;
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

    @RequestMapping(value = "/certApply",method = {RequestMethod.POST})
    public @ResponseBody Object certApply(@RequestBody MemberCert req){
        service.certApply(req);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/withdrawApply",method = {RequestMethod.POST})
    public @ResponseBody Object withdrawApply(@RequestBody WithdrawApply req){
        service.withdrawApply(req);
        return ApiResponse.make();
    }
}
