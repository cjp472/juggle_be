package com.juggle.juggle.primary.cms.service;

import com.juggle.juggle.common.service.CodeCenterService;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.cms.dao.ResourceDao;
import com.juggle.juggle.primary.cms.dto.ResourceDto;
import com.juggle.juggle.primary.cms.dto.ResourceExtra;
import com.juggle.juggle.primary.cms.model.Resource;
import com.juggle.juggle.primary.setting.dao.DictionaryDao;
import com.juggle.juggle.primary.setting.model.Dictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ResourceService extends BaseCRUDService<Resource> {
    @Autowired
    private ResourceDao dao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private CodeCenterService codeCenterService;

    @Override
    protected IRepo<Resource> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(Resource entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genResourceCode());
        }
    }

    @Override
    protected void onUpdate(Resource entity) {
        if(StringUtils.isEmpty(entity.getCode())){
            entity.setCode(codeCenterService.genResourceCode());
        }
    }

    @Cache
    public List<ResourceDto> getResources(String dictKey){
        List<ResourceDto> dtos = new ArrayList<>();
        Dictionary dictionary = dictionaryDao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.CMS,dictKey);
        List<String> strings = Arrays.asList(StringUtils.split(dictionary.getDictValue(),","));
        List<Resource> dictionaries = dao.findAllByCodeInOrderBySortAsc(strings);
        for (Resource resource : dictionaries) {
            ResourceDto dto = new ResourceDto();
            try{
                PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dto,resource,"extra");
                if(!StringUtils.isEmpty(resource.getExtra())){
                    ResourceExtra extra = JsonUtils.readValue(resource.getExtra(),ResourceExtra.class);
                    dto.setExtra(extra);
                }
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析错误");
            }
            dtos.add(dto);
        }
        return dtos;
    }
}
