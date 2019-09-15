package com.juggle.juggle.primary.approval.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.approval.model.Approval;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalDao extends IRepo<Approval> {
    Approval findFirstByMemberIdAndProcessInstanceTypeOrderByIdDesc(Long memberId,String processInstanceType);
}
