package com.juggle.juggle.primary.shop.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.shop.model.ShopProductComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopProductCommentDao extends IRepo<ShopProductComment> {
    List<ShopProductComment> findFirst20ByProductIdOrderByIdDesc(Long productId);
}
