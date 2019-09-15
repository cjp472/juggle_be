package com.juggle.juggle.primary.business.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.business.model.SmsCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsCodeDao extends IRepo<SmsCode> {
    List<SmsCode> findByMobileAndStatusOrderByIdDesc(String mobile, String status);
}
