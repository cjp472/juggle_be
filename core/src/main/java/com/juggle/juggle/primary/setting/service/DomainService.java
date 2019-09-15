package com.juggle.juggle.primary.setting.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.setting.dao.DomainDao;
import com.juggle.juggle.primary.setting.dto.DomainRelation;
import com.juggle.juggle.primary.setting.model.Domain;
import com.juggle.juggle.primary.system.dto.TreeSelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DomainService extends BaseCRUDService<Domain> {
    @Autowired
    private DomainDao dao;

    @Override
    protected IRepo<Domain> getRepo() {
        return dao;
    }

    @Override
    protected void onUpdate(Domain entity) {
        if(entity.getId().equals(entity.getParentId())){
            throw new ServiceException(1001,"上级区域不能是自己");
        }
    }

    @Cache
    public List<DomainRelation> getRelation() throws Exception{
        List<Domain> domains = dao.findAllByParentIdIsNullAndEnabled(true);
        List<DomainRelation> destDomains = new ArrayList<>();
        for (Domain domain : domains) {
            DomainRelation dest = new DomainRelation();
            PropertyCopyUtil.getInstance().copyProperties(dest,domain);
            destDomains.add(dest);
        }
        destDomains = getRelationChildren(destDomains);
        return destDomains;
    }

    public List<TreeSelectItem> getTreeSelect(Long id){
        List<Domain> domains = dao.findAll();
        List<TreeSelectItem> treeSelectItems = new ArrayList<>();
        for(Domain domain:domains){
            if(domain.getId()!=id){
                TreeSelectItem treeSelectItem = new TreeSelectItem();
                treeSelectItem.setLabel(domain.getName());
                treeSelectItem.setValue(domain.getId());
                if(null!=domain.getParentId()){
                    treeSelectItem.setParent(domain.getParentId());
                }else{
                    treeSelectItem.setParent(Long.valueOf(0));
                }
                treeSelectItems.add(treeSelectItem);
            }
        }
        return treeSelectItems;
    }

    public List<Domain> getTree(List<ValueFilter> filters){
        Optional<ValueFilter> nameOtional = filters.stream().filter(filter->filter.getName().equals("name")).findFirst();
        Optional<ValueFilter> enabledOptional  = filters.stream().filter(filter->filter.getName().equals("enabled")).findFirst();
        ValueFilter nameFilter = nameOtional.isPresent()&&nameOtional.get().getValue()!=null?nameOtional.get():null;
        ValueFilter enabledFilter = enabledOptional.isPresent()&&enabledOptional.get().getValue()!=null?enabledOptional.get():null;
        List<Domain> domains;
        if(null==nameFilter && null==enabledFilter){
            domains = dao.findAllByParentIdIsNull();
        }else if(null!=nameFilter && null==enabledFilter){
            domains = dao.findAllByParentIdIsNullAndNameLike(nameFilter.getValue().toString());
        }else if(null==nameFilter && null!=enabledFilter){
            domains = dao.findAllByParentIdIsNullAndEnabled(Boolean.valueOf(enabledFilter.getValue().toString()));
        }else{
            domains = dao.findAllByParentIdIsNullAndNameLikeAndEnabled(nameFilter.getValue().toString(),Boolean.valueOf(enabledFilter.getValue().toString()));
        }
        domains = getChildren(domains,nameFilter,enabledFilter);
        return domains;
    }

    private List<Domain> getChildren(List<Domain> domains,ValueFilter nameFilter,ValueFilter enabledFilter){
        for(Domain domain:domains){
            domain.setChains(getChains(domain));
            List<Domain> domainList;
            if(null==nameFilter && null==enabledFilter){
                domainList = dao.findAllByParentId(domain.getId());
            }else if(null!=nameFilter && null==enabledFilter){
                domainList = dao.findAllByParentIdAndNameLike(domain.getId(),nameFilter.getValue().toString());
            }else if(null==nameFilter && null!=enabledFilter){
                domainList = dao.findAllByParentIdAndEnabled(domain.getId(),Boolean.valueOf(enabledFilter.getValue().toString()));
            }else{
                domainList = dao.findAllByParentIdAndNameLikeAndEnabled(domain.getId(),nameFilter.getValue().toString(),Boolean.valueOf(enabledFilter.getValue().toString()));
            }
            domain.setChildren(domainList.size()>0?getChildren(domainList,nameFilter,enabledFilter):null);
        }
        return domains;
    }

    private List<DomainRelation> getRelationChildren(List<DomainRelation> domains) throws Exception{
        for(DomainRelation domain:domains){
            List<Domain> domainList = dao.findAllByParentIdAndEnabled(domain.getId(),true);
            List<DomainRelation> destDomains = new ArrayList<>();
            for (Domain orgDomain : domainList) {
                DomainRelation dest = new DomainRelation();
                PropertyCopyUtil.getInstance().copyProperties(dest,orgDomain);
                destDomains.add(dest);
            }
            domain.setChildren(destDomains.size()>0?getRelationChildren(destDomains):null);
        }
        return domains;
    }

    private List<Long> getChains(Domain domain){
        List<Long> chains = new ArrayList<>();
        while(null!=domain.getParentId()){
            domain = dao.findOne(domain.getParentId());
            if(null!=domain){
                chains.add(0,domain.getId());
            }
        }
        return chains;
    }


    public void enable(Long id){
        Domain domain = dao.findOne(id);
        domain.setEnabled(true);
        update(id,domain);
    }

    public void disable(Long id){
        Domain domain = dao.findOne(id);
        domain.setEnabled(false);
        update(id,domain);
    }
}
