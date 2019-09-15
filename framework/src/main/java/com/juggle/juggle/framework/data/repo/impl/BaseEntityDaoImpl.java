package com.juggle.juggle.framework.data.repo.impl;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.EntityManager;
import com.juggle.juggle.framework.data.entity.SoftDelete;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.filter.Criteria;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.filter.ValueFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseEntityDaoImpl<T>  extends SimpleJpaRepository<T, Serializable> implements IRepo<T> {

	public BaseEntityDaoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	public BaseEntityDaoImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}

	@Override
	public PageResult<T> search(PageSearch search, Criteria.CriteriaModifier cm) {
		PageRequest req = new PageRequest(search.getPage(), search.getSize());
		if (SoftDelete.class.isAssignableFrom(this.getDomainClass())) {
			search.getFilters().add(new ValueFilter(SoftDelete.FIELD, ValueFilter.OP_EQ, 0L));
		}
		Page<T> page = this.findAll(new Criteria<T>(search, cm), req);
		
		return PageResult.from(page);
	}
	
	@Override
	public PageResult<T> search(PageSearch search) {
		return this.search(search, null);
	}

	public void deleteInIdBatch(Iterable<Serializable> ids)
	{
		for (Serializable id : ids)
		{
			super.deleteById(id);
		}
	}

	@Override
	public void deleteById(Serializable id) {
		T entity = this.findOne(id);
		if (null  != entity) {
			this.delete(entity);
		}
	}

	@Override
	public void delete(T entity) {
		if (entity instanceof SoftDelete) {
			((SoftDelete) entity).setDeleted(System.currentTimeMillis());
			this.save(entity);
		}
		else {
			super.delete(entity);
		}
	}

	@Override
	public T findOne(Serializable id) {
		Optional<T> optional = this.findById(id);
		return optional.isPresent()? optional.get() : null;
	}
}
