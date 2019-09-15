package com.juggle.juggle.primary.community.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.common.service.CodeCenterService;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.community.dao.CommunityDao;
import com.juggle.juggle.primary.community.dto.CommunityDto;
import com.juggle.juggle.primary.community.model.Community;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService extends BaseCRUDService<Community> {
    @Autowired
    private CommunityDao dao;

    @Autowired
    private CodeCenterService codeCenterService;

    @Override
    protected IRepo<Community> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(Community entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genCommunityCode());
        }
    }

    @Cache
    public List<CommunityDto> readAllCommunity() throws Exception{
        List<Community> communities = dao.findAllByEnabledOrderBySort(true);
        List<CommunityDto> dtos = new ArrayList<>();
        for (Community community : communities) {
            CommunityDto dto = new CommunityDto();
            PropertyCopyUtil.getInstance().copyProperties(dto,community);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<SelectItem> readAllEnabled(){
        List<Community> communities = dao.findAllByEnabled(true);
        List<SelectItem> selectDtos = new ArrayList<>();
        for(Community community:communities){
            SelectItem dto = new SelectItem();
            dto.setLabel(community.getName());
            dto.setValue(community.getId());
            selectDtos.add(dto);
        }
        return selectDtos;
    }

    public void enable(Long id){
        Community community = dao.findOne(id);
        community.setEnabled(true);
        update(id,community);
    }

    public void disable(Long id){
        Community community = dao.findOne(id);
        community.setEnabled(false);
        update(id,community);
    }
}
