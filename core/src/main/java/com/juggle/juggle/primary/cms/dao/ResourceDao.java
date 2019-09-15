package com.juggle.juggle.primary.cms.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.cms.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceDao extends IRepo<Resource> {
    List<Resource> findAllByCodeInOrderBySortAsc(List<String> codes);
}
