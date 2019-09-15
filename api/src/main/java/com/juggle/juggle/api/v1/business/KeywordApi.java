package com.juggle.juggle.api.v1.business;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.business.model.Keyword;
import com.juggle.juggle.primary.business.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/business/keyword")
public class KeywordApi extends BaseApi<Keyword,Keyword,Keyword> {
    @Autowired
    private KeywordService service;

    @Override
    protected ICRUDService<Keyword> getService() {
        return service;
    }
}
