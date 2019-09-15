package com.juggle.juggle.primary.business.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.business.dao.KeywordDao;
import com.juggle.juggle.primary.business.model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeywordService extends BaseCRUDService<Keyword> {
    @Autowired
    private KeywordDao dao;

    @Override
    protected IRepo<Keyword> getRepo() {
        return dao;
    }

    @Cache
    public List<Keyword> getHotSearch() throws Exception{
        List<Keyword> keywords = dao.findAllByType(Constants.KEYWORD_TYPE.HOT_SEARCH);
        List<Keyword> hotsearchs = new ArrayList<>();
        for (Keyword keyword : keywords) {
            Keyword dest = new Keyword();
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dest,keyword,"id","type","updatedTime","updatedBy","createdTime","createdBy");
            hotsearchs.add(dest);
        }
        return hotsearchs;
    }
}
