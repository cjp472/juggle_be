package com.juggle.juggle.primary.app.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.app.model.Member;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MemberDao extends IRepo<Member> {
    boolean existsByCode(String code);

    boolean existsByMobile(String mobile);

    Member findFirstByMobile(String mobile);

    Member findFirstByRelationId(Long relation);

    Member findFirstByCode(String code);

    Member findFirstByIdAndStatus(Long id,String status);

    boolean existsByRelationId(Long relationId);

    int deleteByMobile(String mobile);

    boolean existsByNickNameLike(String nickName);

    boolean existsByMobileLike(String mobile);

    List<Member> findAllByGrade(String grade);

    List<Member> findAllByGradeAndTermTimeLessThan(String grade, Date termTime);
}
