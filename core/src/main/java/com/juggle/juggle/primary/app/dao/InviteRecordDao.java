package com.juggle.juggle.primary.app.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.app.model.InviteRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRecordDao extends IRepo<InviteRecord> {
    List<InviteRecord> findAllByHandle(boolean handle);
}
