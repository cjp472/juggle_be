package com.juggle.juggle.primary.shop.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.shop.model.ShopType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopTypeDao extends IRepo<ShopType> {
    List<ShopType> findAllByParentIdIsNull();

    List<ShopType> findAllByParentIdIsNullAndNameLike(String name);

    List<ShopType> findAllByParentIdIsNullAndEnabled(boolean enabled);

    List<ShopType> findAllByParentIdIsNullAndNameLikeAndEnabled(String name,boolean enabled);

    List<ShopType> findAllByParentId(Long parentId);

    List<ShopType> findAllByParentIdAndNameLike(Long parentId,String name);

    List<ShopType> findAllByParentIdAndEnabled(Long parentId,boolean enabled);

    List<ShopType> findAllByParentIdAndNameLikeAndEnabled(Long parentId,String name,boolean enabled);
}
