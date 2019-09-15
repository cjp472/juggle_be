package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.taobao.dao.TaobaoBrandDao;
import com.juggle.juggle.primary.taobao.dao.TaobaoCategoryDao;
import com.juggle.juggle.primary.taobao.dao.TaobaoProductDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoSimplify;
import com.juggle.juggle.primary.taobao.dto.TaobaoSummary;
import com.juggle.juggle.primary.taobao.model.TaobaoProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaobaoProductService extends BaseCRUDService<TaobaoProduct> {
    @Autowired
    private TaobaoProductDao dao;

    @Autowired
    private TaobaoBrandDao taobaoBrandDao;

    @Autowired
    private TaobaoCategoryDao taobaoCategoryDao;

    @Override
    protected IRepo<TaobaoProduct> getRepo() {
        return dao;
    }

    public TaobaoSummary getSummary(){
        TaobaoSummary taobaoSummary = new TaobaoSummary();
        taobaoSummary.setTaobaoTypeNum(taobaoCategoryDao.count());
        taobaoSummary.setTaobaoBrandNum(taobaoBrandDao.count());
        taobaoSummary.setTaobaoProductNum(dao.count());
        return taobaoSummary;
    }

    @Cache
    public PageResult searchSimplify(PageSearch pageSearch){
        PageResult pageResult = search(pageSearch);
        List<TaobaoProduct> products = pageResult.getRows();
        List<TaobaoSimplify> productSimplifies = new ArrayList<>();
        for (TaobaoProduct product : products) {
            TaobaoSimplify productSimplify = new TaobaoSimplify();
            try{
                PropertyCopyUtil.getInstance().copyProperties(productSimplify,product);
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析失败");
            }
            productSimplifies.add(productSimplify);
        }
        pageResult.setRows(productSimplifies);
        return pageResult;
    }
}
