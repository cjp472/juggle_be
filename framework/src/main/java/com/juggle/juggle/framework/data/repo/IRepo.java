package com.juggle.juggle.framework.data.repo;

import java.io.Serializable;

import com.juggle.juggle.framework.data.filter.Criteria;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface IRepo<T> extends JpaRepository<T, Serializable>, JpaSpecificationExecutor<T> {
	PageResult<T> search(PageSearch search);
	PageResult<T> search(PageSearch search, Criteria.CriteriaModifier cm);
	T findOne(Serializable id);
}
