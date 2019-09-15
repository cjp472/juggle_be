package com.juggle.juggle.primary.system.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.system.model.Position;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionDao extends IRepo<Position> {
    List<Position> findAllByEnabled(boolean enabled);
}
