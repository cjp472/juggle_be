package com.juggle.juggle.primary.taobao.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.common.service.CodeCenterService;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.system.dto.TreeSelectItem;
import com.juggle.juggle.primary.taobao.dao.TaobaoTypeDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoTypeDto;
import com.juggle.juggle.primary.taobao.model.TaobaoType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaobaoTypeService extends BaseCRUDService<TaobaoType> {
    @Autowired
    private TaobaoTypeDao dao;

    @Autowired
    private CodeCenterService codeCenterService;

    @Override
    protected IRepo<TaobaoType> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(TaobaoType entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genTaobaoTypeCode());
        }
    }

    @Override
    protected void onUpdate(TaobaoType entity) {
        if(entity.getId().equals(entity.getParentId())){
            throw new ServiceException(1001,"上级商品分类不能是自己");
        }
    }

    public List<SelectItem> readAllRootEnabled(){
        List<SelectItem> selectItems = new ArrayList<>();
        List<TaobaoType> taobaoTypes = dao.findAllByParentIdIsNullAndEnabledOrderBySort(true);
        for (TaobaoType taobaoType : taobaoTypes) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(taobaoType.getName());
            selectItem.setValue(taobaoType.getId());
            selectItems.add(selectItem);
        }
        return selectItems;
    }

    public List<SelectItem> readAllEnabled(Long parentId){
        List<SelectItem> selectItems = new ArrayList<>();
        List<TaobaoType> taobaoTypes = dao.findAllByParentIdAndEnabledOrderBySort(parentId,true);
        for (TaobaoType taobaoType : taobaoTypes) {
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(taobaoType.getName());
            selectItem.setValue(taobaoType.getId());
            selectItems.add(selectItem);
        }
        return selectItems;
    }

    public List<TaobaoType> getTree(List<ValueFilter> filters){
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
        List<TaobaoType> productTypes;
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

    private List<TaobaoType> getChildren(List<TaobaoType> productTypes,ValueFilter nameFilter,ValueFilter enabledFilter){
        for(TaobaoType productType:productTypes){
            productType.setChains(getChains(productType));
            List<TaobaoType> productTypeList;
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
        List<TaobaoType> productTypes = dao.findAll();
        List<TreeSelectItem> treeSelectItems = new ArrayList<>();
        for(TaobaoType productType:productTypes){
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
        TaobaoType productType = dao.findOne(id);
        productType.setEnabled(true);
        update(id,productType);
    }

    public void disable(Long id) {
        TaobaoType productType = dao.findOne(id);
        productType.setEnabled(false);
        update(id,productType);
    }

    @Cache
    public List<TaobaoTypeDto> getPrimary() throws Exception{
        List<TaobaoType> taobaoTypes = dao.findAllByParentIdIsNullAndEnabledOrderBySort(true);
        List<TaobaoTypeDto> dtos = new ArrayList<>();
        for (TaobaoType taobaoType : taobaoTypes) {
            TaobaoTypeDto dto = new TaobaoTypeDto();
            PropertyCopyUtil.getInstance().copyProperties(dto,taobaoType);
            dtos.add(dto);
        }
        return dtos;
    }

    @Cache
    public List<TaobaoTypeDto> getSecondary(Long parentId) throws Exception{
        List<TaobaoType> taobaoTypes = dao.findAllByParentIdAndEnabledOrderBySort(parentId,true);
        List<TaobaoTypeDto> dtos = new ArrayList<>();
        for (TaobaoType taobaoType : taobaoTypes) {
            TaobaoTypeDto dto = new TaobaoTypeDto();
            PropertyCopyUtil.getInstance().copyProperties(dto,taobaoType);
            dtos.add(dto);
        }
        return dtos;
    }

    private List<Long> getChains(TaobaoType productType){
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
