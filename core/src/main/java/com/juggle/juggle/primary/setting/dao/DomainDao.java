package com.juggle.juggle.primary.setting.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.setting.model.Domain;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomainDao extends IRepo<Domain> {
    Domain findFirstByName(String name);

    Domain findFirstByNameAndLevel(String name,Integer level);

    List<Domain> findAllByParentIdIsNull();

    List<Domain> findAllByParentIdIsNullAndNameLike(String name);

    List<Domain> findAllByParentIdIsNullAndEnabled(boolean enabled);

    List<Domain> findAllByParentIdIsNullAndNameLikeAndEnabled(String name,boolean enabed);

    List<Domain> findAllByParentId(Long parentId);

    List<Domain> findAllByParentIdAndNameLike(Long parentId,String name);

    List<Domain> findAllByParentIdAndEnabled(Long parentId,boolean enabled);

    List<Domain> findAllByParentIdAndNameLikeAndEnabled(Long parentId,String name,boolean enabled);
}
