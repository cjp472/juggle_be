package com.juggle.juggle.primary.business.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.business.model.Push;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushDao extends IRepo<Push> {
    List<Push> findAllByStatus(String status);
}
