package com.juggle.juggle.primary.auth.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.auth.model.MemberAuth;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAuthDao extends IRepo<MemberAuth> {
    MemberAuth findFirstByMemberId(Long memberId);
}
