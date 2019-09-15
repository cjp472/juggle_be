package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.taobao.dao.TaobaoOrderDao;
import com.juggle.juggle.primary.taobao.model.TaobaoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaobaoOrderService extends BaseCRUDService<TaobaoOrder> {
    @Autowired
    private TaobaoOrderDao dao;

    @Override
    protected IRepo<TaobaoOrder> getRepo() {
        return dao;
    }
}
