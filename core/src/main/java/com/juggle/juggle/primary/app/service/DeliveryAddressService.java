package com.juggle.juggle.primary.app.service;

import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.app.dao.DeliveryAddressDao;
import com.juggle.juggle.primary.app.dto.DeliveryAddressDto;
import com.juggle.juggle.primary.app.model.DeliveryAddress;
import com.juggle.juggle.primary.app.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryAddressService extends BaseCRUDService<DeliveryAddress> {
    @Autowired
    private DeliveryAddressDao dao;

    @Override
    protected IRepo<DeliveryAddress> getRepo() {
        return dao;
    }

    @Override
    protected void afterCreated(DeliveryAddress entity) {
        if(entity.isPresent()){
            Member member = (Member)UserSession.getAuthorize().getUser();
            dao.updatePresent(member.getId(),entity.getId());
        }
    }

    @Override
    protected void afterUpdated(DeliveryAddress entity) {
        if(entity.isPresent()){
            Member member = (Member)UserSession.getAuthorize().getUser();
            dao.updatePresent(member.getId(),entity.getId());
        }
    }

    public List<DeliveryAddressDto> readAllAddress() throws Exception{
        Member member = (Member) UserSession.getAuthorize().getUser();
        List<DeliveryAddress> addresses = dao.findAllByMemberId(member.getId());
        List<DeliveryAddressDto> dtos = new ArrayList<>();
        for (DeliveryAddress address : addresses) {
            DeliveryAddressDto dto = new DeliveryAddressDto();
            PropertyCopyUtil.getInstance().copyProperties(dto,address);
            dtos.add(dto);
        }
        return dtos;
    }
}
