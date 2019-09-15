package com.juggle.juggle.primary.system.service;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.system.dao.OrganizationDao;
import com.juggle.juggle.primary.system.dto.TreeSelectItem;
import com.juggle.juggle.primary.system.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService extends BaseCRUDService<Organization> {
    @Autowired
    private OrganizationDao dao;

    @Override
    protected IRepo<Organization> getRepo() {
        return dao;
    }

    @Override
    protected void onUpdate(Organization entity) {
        if(entity.getId().equals(entity.getParentId())){
            throw new ServiceException(1001,"上级组织机构不能是自己");
        }
    }

    public List<TreeSelectItem> getTreeSelect(Long id){
        List<Organization> organizations = dao.findAll();
        List<TreeSelectItem> treeSelectItems = new ArrayList<>();
        for(Organization organization:organizations){
            if(organization.getId()!=id){
                TreeSelectItem treeSelectItem = new TreeSelectItem();
                treeSelectItem.setLabel(organization.getName());
                treeSelectItem.setValue(organization.getId());
                if(null!=organization.getParentId()){
                    treeSelectItem.setParent(organization.getParentId());
                }else{
                    treeSelectItem.setParent(Long.valueOf(0));
                }
                treeSelectItems.add(treeSelectItem);
            }
        }
        return treeSelectItems;
    }

    public List<Organization> getTree(List<ValueFilter> filters){
        Optional<ValueFilter> nameOtional = filters.stream().filter(filter->filter.getName().equals("name")).findFirst();
        Optional<ValueFilter> enabledOptional  = filters.stream().filter(filter->filter.getName().equals("enabled")).findFirst();
        ValueFilter nameFilter = nameOtional.isPresent()&&nameOtional.get().getValue()!=null?nameOtional.get():null;
        ValueFilter enabledFilter = enabledOptional.isPresent()&&enabledOptional.get().getValue()!=null?enabledOptional.get():null;
        List<Organization> organizations;
        if(null==nameFilter && null==enabledFilter){
            organizations = dao.findAllByParentIdIsNull();
        }else if(null!=nameFilter && null==enabledFilter){
            organizations = dao.findAllByParentIdIsNullAndNameLike(nameFilter.getValue().toString());
        }else if(null==nameFilter && null!=enabledFilter){
            organizations = dao.findAllByParentIdIsNullAndEnabled(Boolean.valueOf(enabledFilter.getValue().toString()));
        }else{
            organizations = dao.findAllByParentIdIsNullAndNameLikeAndEnabled(nameFilter.getValue().toString(),Boolean.valueOf(enabledFilter.getValue().toString()));
        }
        organizations = getChildren(organizations,nameFilter,enabledFilter);
        return organizations;
    }

    private List<Organization> getChildren(List<Organization> organizations,ValueFilter nameFilter,ValueFilter enabledFilter){
        for(Organization organization:organizations){
            organization.setChains(getChains(organization));
            List<Organization> organizationList;
            if(null==nameFilter && null==enabledFilter){
                organizationList = dao.findAllByParentId(organization.getId());
            }else if(null!=nameFilter && null==enabledFilter){
                organizationList = dao.findAllByParentIdAndNameLike(organization.getId(),nameFilter.getValue().toString());
            }else if(null==nameFilter && null!=enabledFilter){
                organizationList = dao.findAllByParentIdAndEnabled(organization.getId(),Boolean.valueOf(enabledFilter.getValue().toString()));
            }else{
                organizationList = dao.findAllByParentIdAndNameLikeAndEnabled(organization.getId(),nameFilter.getValue().toString(),Boolean.valueOf(enabledFilter.getValue().toString()));
            }
            organization.setChildren(organizationList.size()>0?getChildren(organizationList,nameFilter,enabledFilter):null);
        }
        return organizations;
    }

    private List<Long> getChains(Organization organization){
        List<Long> chains = new ArrayList<>();
        while(null!=organization.getParentId()){
            organization = dao.findOne(organization.getParentId());
            if(null!=organization){
                chains.add(0,organization.getId());
            }
        }
        return chains;
    }

    public void enable(Long id){
        Organization organization = dao.findOne(id);
        organization.setEnabled(true);
        update(id,organization);
    }

    public void disable(Long id){
        Organization organization = dao.findOne(id);
        organization.setEnabled(false);
        update(id,organization);
    }
}
