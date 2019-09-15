package com.juggle.juggle.api.v1.account;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.account.dto.BankAccountDto;
import com.juggle.juggle.primary.account.model.BankAccount;
import com.juggle.juggle.primary.account.service.BankAccountService;
import com.juggle.juggle.primary.app.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/account/bankAccount")
public class BankAccountApi extends BaseApi<BankAccount,BankAccount,BankAccount> {
    @Autowired
    private BankAccountService service;

    @Override
    protected ICRUDService<BankAccount> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllMy",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object readAllMy() throws Exception{
        List<BankAccountDto> bankAccountDtos = service.readAllMy();
        return ApiResponse.make(bankAccountDtos);
    }

    @RequestMapping(value = "/create",method = {RequestMethod.POST})
    public @ResponseBody Object create(@RequestBody @Valid BankAccount obj){
        Member member = (Member) UserSession.getAuthorize().getUser();
        obj.setMemberId(member.getId());
        return ApiResponse.make(this.getService().create(obj));
    }
}
