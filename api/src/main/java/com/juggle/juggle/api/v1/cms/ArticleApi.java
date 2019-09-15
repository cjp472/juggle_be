package com.juggle.juggle.api.v1.cms;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.cms.model.Article;
import com.juggle.juggle.primary.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/cms/article")
public class ArticleApi extends BaseApi<Article,Article,Article> {
    @Autowired
    private ArticleService service;

    @Override
    protected ICRUDService<Article> getService() {
        return service;
    }
}
