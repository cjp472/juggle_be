package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaobaoTypeDao extends IRepo<TaobaoType> {
    List<TaobaoType> findAllByParentIdIsNull();

    List<TaobaoType> findAllByParentIdIsNullAndNameLike(String name);

    List<TaobaoType> findAllByParentIdIsNullAndEnabled(boolean enabled);

    List<TaobaoType> findAllByParentIdIsNullAndNameLikeAndEnabled(String name,boolean enabled);

    List<TaobaoType> findAllByParentId(Long parentId);

    List<TaobaoType> findAllByParentIdAndNameLike(Long parentId,String name);

    List<TaobaoType> findAllByParentIdAndEnabled(Long parentId,boolean enabled);

    List<TaobaoType> findAllByParentIdAndNameLikeAndEnabled(Long parentId,String name,boolean enabled);

    List<TaobaoType> findAllByParentIdIsNullAndEnabledOrderBySort(boolean enabled);

    List<TaobaoType> findAllByParentIdAndEnabledOrderBySort(Long parentId,boolean enabled);

    List<TaobaoType> findAllByParentIdIsNotNullAndEnabled(boolean enabled);

    boolean existsByIdAndParentIdIsNull(Long id);
}
