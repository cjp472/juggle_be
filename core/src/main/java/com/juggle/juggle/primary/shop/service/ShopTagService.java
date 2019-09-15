package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.common.service.CodeCenterService;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.shop.dao.ShopTagDao;
import com.juggle.juggle.primary.shop.model.ShopTag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopTagService extends BaseCRUDService<ShopTag> {
    @Autowired
    private ShopTagDao dao;

    @Autowired
    private CodeCenterService codeCenterService;

    @Override
    protected IRepo<ShopTag> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(ShopTag entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genShopTagCode());
        }
    }

    @Override
    protected void onUpdate(ShopTag entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genShopTagCode());
        }
    }

    public List<SelectItem> readAllEnabled(){
        List<SelectItem> selectItems = new ArrayList<>();
        List<ShopTag> tags = dao.findAllByEnabled(true);
        for (ShopTag tag : tags) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(tag.getName());
            selectItem.setValue(tag.getId());
            selectItems.add(selectItem);
        }
        return selectItems;
    }
}
