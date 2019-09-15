package com.juggle.juggle.api.v1.platform;

import com.juggle.juggle.primary.setting.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/platform/aliPay")
public class AliPayApi {
    @Autowired
    private DictionaryService dictionaryService;
}
