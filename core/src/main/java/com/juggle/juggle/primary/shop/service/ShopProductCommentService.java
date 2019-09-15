package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.shop.dao.ShopProductCommentDao;
import com.juggle.juggle.primary.shop.model.ShopProductComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopProductCommentService extends BaseCRUDService<ShopProductComment> {
    @Autowired
    private ShopProductCommentDao dao;

    @Override
    protected IRepo<ShopProductComment> getRepo() {
        return dao;
    }
}
