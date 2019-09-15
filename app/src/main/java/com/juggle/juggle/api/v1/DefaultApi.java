package com.juggle.juggle.api.v1;

import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.primary.app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
public class DefaultApi {
    @Autowired
    private MemberService memberService;

    @GetMapping("/**")
    public @ResponseBody
    Object index() {
        return ApiResponse.make("Server is working");
    }

    @RequestMapping(value = "/remove/{mobile}",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object remove(@PathVariable String mobile){
        memberService.removeByMobile(mobile);
        return ApiResponse.make();
    }
}
