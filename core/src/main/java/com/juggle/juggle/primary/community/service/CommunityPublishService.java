package com.juggle.juggle.primary.community.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.community.dao.CommunityPublishDao;
import com.juggle.juggle.primary.community.dto.CommunityPublishDto;
import com.juggle.juggle.primary.community.model.CommunityPublish;
import com.juggle.juggle.primary.system.dao.UserDao;
import com.juggle.juggle.primary.system.model.User;
import com.juggle.juggle.primary.taobao.dao.TaobaoPopularDao;
import com.juggle.juggle.primary.taobao.dto.TaobaoPopularDto;
import com.juggle.juggle.primary.taobao.model.TaobaoPopular;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityPublishService extends BaseCRUDService<CommunityPublish> {
    @Autowired
    private CommunityPublishDao dao;

    @Autowired
    private TaobaoPopularDao taobaoPopularDao;

    @Autowired
    private UserDao userDao;

    @Override
    protected IRepo<CommunityPublish> getRepo() {
        return dao;
    }

    @Cache
    public PageResult searchPublish(PageSearch pageSearch){
        String sort = pageSearch.getSort();
        if(StringUtils.isEmpty(sort)){
            sort = "id desc";
        }else if(!sort.contains("id")){
            sort = sort + ",id desc";
        }
        pageSearch.setSort(sort);
        PageResult pageResult = search(pageSearch);
        List<CommunityPublish> publishes = pageResult.getRows();
        List<CommunityPublishDto> dtos = new ArrayList<>();
        try{
            for (CommunityPublish publish : publishes) {
                CommunityPublishDto dto = new CommunityPublishDto();
                PropertyCopyUtil.getInstance().copyProperties(dto,publish);
                User user = userDao.findOne(publish.getCreatedBy());
                dto.setAuthor(user.getLoginName());
                dto.setAvatar(user.getAvatar());
                if(null!=publish.getPopularId()){
                    TaobaoPopular popular = taobaoPopularDao.findOne(publish.getPopularId());
                    TaobaoPopularDto taobaoPopularDto = new TaobaoPopularDto();
                    PropertyCopyUtil.getInstance().copyProperties(taobaoPopularDto,popular);
                    taobaoPopularDto.setSub(Constants.TAOBAO_SUB.POPULAR);
                    dto.setProduct(taobaoPopularDto);
                }
                dtos.add(dto);
            }
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        pageResult.setRows(dtos);
        return pageResult;
    }
}
