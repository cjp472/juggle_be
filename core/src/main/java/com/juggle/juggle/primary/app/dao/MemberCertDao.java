package com.juggle.juggle.primary.app.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.app.model.MemberCert;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCertDao extends IRepo<MemberCert> {
    MemberCert findFirstByMemberId(Long memberId);
}
