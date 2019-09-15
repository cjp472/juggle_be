package com.juggle.juggle.primary.advert.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.advert.dao.AdvertTypeDao;
import com.juggle.juggle.primary.advert.model.AdvertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertTypeService extends BaseCRUDService<AdvertType> {
    @Autowired
    private AdvertTypeDao dao;

    @Override
    protected IRepo<AdvertType> getRepo() {
        return dao;
    }

    public List<SelectItem> readAllEnabled(){
        List<AdvertType> advertTypes = dao.findAll();
        List<SelectItem> selectItems = new ArrayList<>();
        for(AdvertType advertType:advertTypes){
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(advertType.getName());
            selectItem.setValue(advertType.getId());
            selectItems.add(selectItem);
        }
        return selectItems;
    }
}
