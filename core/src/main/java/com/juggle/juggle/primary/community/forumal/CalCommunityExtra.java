package com.juggle.juggle.primary.community.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.community.dao.CommunityPublishDao;
import com.juggle.juggle.primary.community.dto.CommunityExtra;
import com.juggle.juggle.primary.community.model.Community;

import java.util.Collection;
import java.util.Date;

public class CalCommunityExtra implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        Community entity = (Community) bean;
        CommunityPublishDao communityPublishDao = ApplicationUtils.getBean(CommunityPublishDao.class);
        Date now = new Date();
        int todayPublish = communityPublishDao.countAllByCommunityIdAndCreatedTimeGreaterThanAndCreatedTimeLessThan(entity.getId(),DateUtils.start(now),DateUtils.goPast(now,0));
        CommunityExtra communityExtra = new CommunityExtra();
        communityExtra.setTodayPublish(todayPublish);
        return communityExtra;
    }
}
