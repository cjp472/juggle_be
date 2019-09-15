package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.common.data.SimpleItem;
import com.juggle.juggle.common.service.CodeCenterService;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.taobao.dao.TaobaoCategoryDao;
import com.juggle.juggle.primary.taobao.model.TaobaoCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaobaoCategoryService extends BaseCRUDService<TaobaoCategory> {
    @Autowired
    private TaobaoCategoryDao dao;

    @Autowired
    private CodeCenterService codeCenterService;

    @Override
    protected IRepo<TaobaoCategory> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(TaobaoCategory entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genTaobaoCategoryCode());
        }
    }

    public List<SelectItem> readAllEnabled(){
        List<SelectItem> selectDtos = new ArrayList<>();
        List<TaobaoCategory> taobaoTypes = dao.findAllByEnabledOrderBySort(true);
        for(TaobaoCategory taobaoType:taobaoTypes){
            SelectItem dto = new SelectItem();
            dto.setLabel(taobaoType.getName());
            dto.setValue(taobaoType.getId());
            selectDtos.add(dto);
        }
        return selectDtos;
    }

    @Cache
    public List<SimpleItem> readAllSimple(){
        List<SimpleItem> simpleItems = new ArrayList<>();
        List<TaobaoCategory> taobaoCategories = dao.findAllByEnabledOrderBySort(true);
        for(TaobaoCategory taobaoCategory:taobaoCategories){
            SimpleItem item = new SimpleItem();
            item.setId(taobaoCategory.getId());
            item.setName(taobaoCategory.getName());
            simpleItems.add(item);
        }
        return simpleItems;
    }

    public void enable(Long id){
        TaobaoCategory taobaoType = dao.findOne(id);
        taobaoType.setEnabled(true);
        update(id,taobaoType);
    }

    public void disable(Long id){
        TaobaoCategory taobaoType = dao.findOne(id);
        taobaoType.setEnabled(false);
        update(id,taobaoType);
    }
}
