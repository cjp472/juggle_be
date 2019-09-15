package com.juggle.juggle.framework.data.filter;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

@Component
public class SimpleQuery {

	@PersistenceContext
	private EntityManager entityManager;
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	public int update(String hql, Map<String, Object> params) {
		Query query = entityManager.createQuery(hql);
		if(params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.executeUpdate();
	}
	
	public Long count(String countHql, Map<String, Object> parameters)
	{
		Query countQuery = createQuery(countHql, parameters);
		Long count;

		count = ((Long)countQuery.getSingleResult()).longValue();
		return count;
	}

	public Query createQuery(String hql, Map<String, Object> parameters)
	{
		Query query = null;
		query = entityManager.createQuery(hql);
		if (null != parameters)
		{
			for (String key: parameters.keySet())
			{
				query.setParameter(key, parameters.get(key));
			}
		}
		return query;
	}

	public Query createQuery(String hql, Map<String, Object> parameters, Class type)
	{
		Query query = null;
		query = entityManager.createQuery(hql, type);
		if (null != parameters)
		{
			for (String key: parameters.keySet())
			{
				query.setParameter(key, parameters.get(key));
			}
		}
		return query;
	}
	

	public Object queryOne(String hql, Map<String, Object> params) {
		List list = query(hql, params, 0, 1);
		if (list.isEmpty()) {
			return null;
		}
		else {
			return list.get(0);
		}
	}
	
	public List query(String hql, Map<String, Object> params) {
		return this.query(hql, params, 0, 0);
	}

	public List query(String hql, Map<String, Object> params, int from, int size) {
		Query query = entityManager.createQuery(hql);
		if(params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		query.setFirstResult(from);
		if (size > 0) {
			query.setMaxResults(size);
		}
		return query.getResultList();
	}	
	
	public PageResult queryPage(String hql, Map<String, Object> params, int page, int size) {
		PageResult result = new PageResult();
		result.setPage(page);
		result.setSize(size);

		String upperHQL = hql.toUpperCase();
		int index = upperHQL.indexOf("FROM");
		String countHql = "SELECT COUNT(*) " + hql.substring(index);
		Long count = this.count(countHql, params);
		result.setTotal(count);
		
		Query query = entityManager.createQuery(hql);		
		if(params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		
		query.setFirstResult(size * page);
		if (size > 0) {
			query.setMaxResults(size);
		}		
		result.setRows(query.getResultList());
		return result;
	}
}
