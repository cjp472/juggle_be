package com.juggle.juggle.api.v1.business;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.business.model.Keyword;
import com.juggle.juggle.primary.business.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/business/keyword")
public class KeywordApi extends BaseApi<Keyword,Keyword,Keyword> {
    @Autowired
    private KeywordService service;

    @Override
    protected ICRUDService<Keyword> getService() {
        return service;
    }

    @RequestMapping(value = "/hotsearch",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object hotsearch() throws Exception{
        List<Keyword> keywords = service.getHotSearch();
        return ApiResponse.make(keywords);
    }
}
