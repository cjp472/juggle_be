package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.common.service.CodeCenterService;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.shop.dao.ShopTypeDao;
import com.juggle.juggle.primary.shop.model.ShopType;
import com.juggle.juggle.primary.system.dto.TreeSelectItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopTypeService extends BaseCRUDService<ShopType> {
    @Autowired
    private ShopTypeDao dao;

    @Autowired
    private CodeCenterService codeCenterService;

    @Override
    protected IRepo<ShopType> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(ShopType entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genShopTypeCode());
        }
    }

    @Override
    protected void onUpdate(ShopType entity) {
        if(entity.getId().equals(entity.getParentId())){
            throw new ServiceException(1001,"上级商品分类不能是自己");
        }
    }

    public List<ShopType> getTree(List<ValueFilter> filters){
        ValueFilter nameFilter = null;
        ValueFilter enabledFilter = null;
        if(null!=filters && filters.size()>0){
            for(ValueFilter valueFilter:filters){
                if(valueFilter!=null && valueFilter.getName().equals("name") && null!=valueFilter.getValue()){
                    nameFilter = valueFilter;
                }else if(valueFilter!=null && valueFilter.getName().equals("enabled")){
                    enabledFilter = valueFilter;
                }
            }
        }
        List<ShopType> productTypes;
        if(null!=nameFilter && null!=enabledFilter){
            productTypes = dao.findAllByParentIdIsNullAndNameLikeAndEnabled(nameFilter.getValue().toString(),Boolean.valueOf(enabledFilter.getValue().toString()));
        }else if(null==nameFilter && null!=enabledFilter){
            productTypes = dao.findAllByParentIdIsNullAndEnabled(Boolean.valueOf(enabledFilter.getValue().toString()));
        }else if(null!=nameFilter && null==enabledFilter){
            productTypes = dao.findAllByParentIdIsNullAndNameLike(nameFilter.getValue().toString());
        }else{
            productTypes = dao.findAllByParentIdIsNull();
        }
        productTypes = getChildren(productTypes,nameFilter,enabledFilter);
        return productTypes;
    }

    private List<ShopType> getChildren(List<ShopType> productTypes, ValueFilter nameFilter, ValueFilter enabledFilter){
        for(ShopType productType:productTypes){
            productType.setChains(getChains(productType));
            List<ShopType> productTypeList;
            if(null!=nameFilter && null!=enabledFilter){
                productTypeList = dao.findAllByParentIdAndNameLikeAndEnabled(productType.getId(),nameFilter.getValue().toString(),Boolean.valueOf(enabledFilter.getValue().toString()));
            }else if(null==nameFilter && null!=enabledFilter){
                productTypeList = dao.findAllByParentIdAndEnabled(productType.getId(),Boolean.valueOf(enabledFilter.getValue().toString()));
            }else if(null!=nameFilter && null==enabledFilter){
                productTypeList = dao.findAllByParentIdAndNameLike(productType.getId(),nameFilter.getValue().toString());
            }else{
                productTypeList = dao.findAllByParentId(productType.getId());
            }
            if(productTypeList.size()>0){
                productTypeList = getChildren(productTypeList,nameFilter,enabledFilter);
            }
            productType.setChildren(productTypeList.size()>0?getChildren(productTypeList,nameFilter,enabledFilter):null);
        }
        return productTypes;
    }

    public List<TreeSelectItem> getTreeSelect(Long id){
        List<ShopType> productTypes = dao.findAll();
        List<TreeSelectItem> treeSelectItems = new ArrayList<>();
        for(ShopType productType:productTypes){
            if(productType.getId()!=id){
                TreeSelectItem treeSelectItem = new TreeSelectItem();
                treeSelectItem.setValue(productType.getId());
                treeSelectItem.setLabel(productType.getName());
                if(null!=productType.getParentId()){
                    treeSelectItem.setParent(productType.getParentId());
                }else{
                    treeSelectItem.setParent(Long.valueOf(0));
                }
                treeSelectItems.add(treeSelectItem);
            }
        }
        return treeSelectItems;
    }

    public void enable(Long id){
        ShopType shopType = dao.findOne(id);
        shopType.setEnabled(true);
        update(id,shopType);
    }

    public void disable(Long id){
        ShopType shopType = dao.findOne(id);
        shopType.setEnabled(false);
        update(id,shopType);
    }

    private List<Long> getChains(ShopType productType){
        List<Long> chains = new ArrayList<>();
        while(null!=productType.getParentId()) {
            productType = dao.findOne(productType.getParentId());
            if(isNotNull(productType)){
                chains.add(0,productType.getId());
            }
        }
        return chains;
    }

    private boolean isNotNull(Object object){
        if(null!=object){
            return true;
        }
        return false;
    }
}
