package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.taobao.dao.TaobaoGoodsDao;
import com.juggle.juggle.primary.taobao.dao.TaobaoTypeDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoSimplify;
import com.juggle.juggle.primary.taobao.model.TaobaoGoods;
import com.juggle.juggle.primary.taobao.model.TaobaoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaobaoGoodsService extends BaseCRUDService<TaobaoGoods> {
    @Autowired
    private TaobaoGoodsDao dao;

    @Autowired
    private TaobaoTypeDao taobaoTypeDao;

    @Override
    protected IRepo<TaobaoGoods> getRepo() {
        return dao;
    }

    @Cache
    public PageResult searchSimplify(PageSearch pageSearch){
        ValueFilter valueFilter = pageSearch.getFilters().get(0);
        if(valueFilter.getName().equals("typeId")){
            if(taobaoTypeDao.existsByIdAndParentIdIsNull(Long.valueOf(valueFilter.getValue().toString()))){
                List<Long> ids = new ArrayList<>();
                List<TaobaoType> taobaoTypes = taobaoTypeDao.findAllByParentIdAndEnabled(Long.valueOf(valueFilter.getValue().toString()),true);
                for (TaobaoType taobaoType : taobaoTypes) {
                    ids.add(taobaoType.getId());
                }
                List<ValueFilter> filters = new ArrayList<>();
                ValueFilter filter = new ValueFilter("typeId",ValueFilter.OP_IN,ids);
                filters.add(filter);
                pageSearch.setFilters(filters);
            }
        }
        PageResult pageResult = search(pageSearch);
        List<TaobaoGoods> products = pageResult.getRows();
        List<TaobaoSimplify> productSimplifies = new ArrayList<>();
        for (TaobaoGoods product : products) {
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
