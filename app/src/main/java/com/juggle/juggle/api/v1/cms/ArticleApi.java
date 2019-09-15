package com.juggle.juggle.api.v1.cms;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.cms.model.Article;
import com.juggle.juggle.primary.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/cms/article")
public class ArticleApi extends BaseApi<Article,Article,Article> {
    @Autowired
    private ArticleService service;

    @Override
    protected ICRUDService<Article> getService() {
        return service;
    }

    @RequestMapping(value = "/findNoviceGuide",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object findNoviceGuide() throws Exception{
        List<Article> articles =  service.getNoviceGuide();
        return ApiResponse.make(articles);
    }
}
