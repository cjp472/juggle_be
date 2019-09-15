package com.juggle.juggle.primary.community.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.community.model.CommunityPublish;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CommunityPublishDao extends IRepo<CommunityPublish> {
    int countAllByCommunityIdAndCreatedTimeGreaterThanAndCreatedTimeLessThan(Long communityId, Date startTime,Date endTime);
}
