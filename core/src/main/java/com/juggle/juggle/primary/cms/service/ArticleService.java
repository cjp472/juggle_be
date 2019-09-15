package com.juggle.juggle.primary.cms.service;

import com.juggle.juggle.common.service.CodeCenterService;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.cms.dao.ArticleDao;
import com.juggle.juggle.primary.cms.model.Article;
import com.juggle.juggle.primary.setting.dao.DictionaryDao;
import com.juggle.juggle.primary.setting.model.Dictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService extends BaseCRUDService<Article> {
    @Autowired
    private ArticleDao dao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private CodeCenterService codeCenterService;

    @Override
    protected IRepo<Article> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(Article entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genArticleCode());
        }
    }

    @Override
    protected void onUpdate(Article entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genArticleCode());
        }
    }

    @Cache
    public List<Article> getNoviceGuide() throws Exception{
        Dictionary dictionary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.CMS,"CMS_NOVICE_GUIDE");
        List<String> strings = Arrays.asList(StringUtils.split(dictionary.getDictValue(),","));
        List<Article> articles = dao.findAllByCodeInOrderBySortAsc(strings);
        List<Article> dests = new ArrayList<>();
        for (Article article : articles) {
            Article dest = new Article();
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dest,article,"updatedBy","updatedTime","createdBy","createdTime");
            dests.add(dest);
        }
        return dests;
    }
}
