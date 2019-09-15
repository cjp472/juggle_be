package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.common.util.TaobaoClientUtil;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.taobao.dao.TaobaoBrandDao;
import com.juggle.juggle.primary.taobao.dao.TaobaoProductDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoBrandDto;
import com.juggle.juggle.primary.taobao.dto.TaobaoBrandItem;
import com.juggle.juggle.primary.taobao.model.TaobaoBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaobaoBrandService extends BaseCRUDService<TaobaoBrand> {
    @Autowired
    private TaobaoBrandDao dao;

    @Autowired
    private TaobaoProductDao taobaoProductDao;

    @Override
    protected IRepo<TaobaoBrand> getRepo() {
        return dao;
    }

    public List<SelectItem> readAllEnabled(Long categoryId){
        List<TaobaoBrand> taobaoBrands = dao.findAllByEnabledAndCategoryId(true,categoryId);
        List<SelectItem> items = new ArrayList<>();
        for(TaobaoBrand taobaoBrand:taobaoBrands){
            SelectItem item = new SelectItem();
            item.setLabel(taobaoBrand.getName());
            item.setValue(taobaoBrand.getId());
            items.add(item);
        }
        return items;
    }

    @Cache
    public List<TaobaoBrandItem> readAllEnabledByCategoryId(Long categoryId) throws Exception{
        List<TaobaoBrand> taobaoBrands = dao.findAllByEnabledAndCategoryId(true,categoryId);
        List<TaobaoBrandItem> taobaoBrandItems = new ArrayList<>();
        for (TaobaoBrand taobaoBrand : taobaoBrands) {
            TaobaoBrandItem taobaoBrandItem = new TaobaoBrandItem();
            PropertyCopyUtil.getInstance().copyProperties(taobaoBrandItem,taobaoBrand);
            taobaoBrandItems.add(taobaoBrandItem);
        }
        return taobaoBrandItems;
    }

    @Cache
    public TaobaoBrandDto readDetail(Long id){
        TaobaoBrand taobaoBrand = dao.findOne(id);
        TaobaoBrandDto dto = new TaobaoBrandDto();
        try{
            PropertyCopyUtil.getInstance().copyProperties(dto,taobaoBrand);
        }catch(Exception e){
            throw new ServiceException(1001,e.getMessage());
        }
        return dto;
    }

    public void sync(Long id){
        TaobaoBrand taobaoBrand = dao.findOne(id);
        TaobaoClientUtil taobaoClientUtils = new TaobaoClientUtil();
        taobaoProductDao.deleteAllByBrandId(id);
        taobaoClientUtils.grabTaobaoProduct(taobaoBrand);
    }

    public void enable(Long id){
        TaobaoBrand taobaoBrand = dao.findOne(id);
        taobaoBrand.setEnabled(true);
        update(id,taobaoBrand);
    }

    public void disable(Long id){
        TaobaoBrand taobaoBrand = dao.findOne(id);
        taobaoBrand.setEnabled(false);
        update(id,taobaoBrand);
    }
}
