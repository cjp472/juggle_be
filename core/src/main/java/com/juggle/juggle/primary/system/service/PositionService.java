package com.juggle.juggle.primary.system.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.system.dao.PositionDao;
import com.juggle.juggle.primary.system.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionService extends BaseCRUDService<Position> {
    @Autowired
    private PositionDao dao;

    @Override
    protected IRepo<Position> getRepo() {
        return dao;
    }

    public List<SelectItem> readAllEnabled(){
        List<SelectItem> selectDtos = new ArrayList<>();
        List<Position> positions = dao.findAllByEnabled(true);
        for(Position position:positions){
            SelectItem dto = new SelectItem();
            dto.setLabel(position.getName());
            dto.setValue(position.getId());
            selectDtos.add(dto);
        }
        return selectDtos;
    }

    public void enable(Long id){
        Position position = dao.findOne(id);
        position.setEnabled(true);
        update(id,position);
    }

    public void disable(Long id){
        Position position = dao.findOne(id);
        position.setEnabled(false);
        update(id,position);
    }
}
