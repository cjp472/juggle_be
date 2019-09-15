package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.taobao.dao.TaobaoFavourableDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoSimplify;
import com.juggle.juggle.primary.taobao.model.TaobaoFavourable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaobaoFavourableService extends BaseCRUDService<TaobaoFavourable> {
    @Autowired
    private TaobaoFavourableDao dao;

    @Override
    protected IRepo<TaobaoFavourable> getRepo() {
        return dao;
    }

    public PageResult searchSimplify(PageSearch pageSearch){
        PageResult pageResult = search(pageSearch);
        List<TaobaoFavourable> products = pageResult.getRows();
        List<TaobaoSimplify> productSimplifies = new ArrayList<>();
        for (TaobaoFavourable product : products) {
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
