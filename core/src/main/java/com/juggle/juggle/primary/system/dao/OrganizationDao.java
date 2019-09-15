package com.juggle.juggle.primary.system.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.system.model.Organization;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationDao extends IRepo<Organization> {
    List<Organization> findAllByParentIdIsNull();

    List<Organization> findAllByParentIdIsNullAndNameLike(String name);

    List<Organization> findAllByParentIdIsNullAndEnabled(boolean enabled);

    List<Organization> findAllByParentIdIsNullAndNameLikeAndEnabled(String name,boolean enabled);

    List<Organization> findAllByParentId(Long parentId);

    List<Organization> findAllByParentIdAndNameLike(Long parentId,String name);

    List<Organization> findAllByParentIdAndEnabled(Long parentId,boolean enabled);

    List<Organization> findAllByParentIdAndNameLikeAndEnabled(Long parentId,String name,boolean enabled);
}
