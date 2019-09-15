package com.juggle.juggle.primary.cms.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.cms.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends IRepo<Article> {
    List<Article> findAllByCodeInOrderBySortAsc(List<String> codes);

    boolean existsByTitleLike(String title);

    boolean existsByBriefLike(String title);
}
