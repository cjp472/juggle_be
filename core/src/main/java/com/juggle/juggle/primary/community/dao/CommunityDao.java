package com.juggle.juggle.primary.community.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.community.model.Community;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityDao extends IRepo<Community> {
    List<Community> findAllByEnabled(boolean enabled);

    List<Community> findAllByEnabledOrderBySort(boolean enabled);
}
