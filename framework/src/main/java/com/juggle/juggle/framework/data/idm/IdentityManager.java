package com.juggle.juggle.framework.data.idm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IdentityManager {

	@Autowired
	@Qualifier("entityManagerFactoryPrimary")
	private AbstractEntityManagerFactoryBean emf;

	public long next(String name) {
		return findNIncrease(name);
	}

	private long findNIncrease(String name) {
		EntityManager em = null;
		List<?> rets = null;
		try {
			name = name.toUpperCase();
			em = emf.getObject().createEntityManager();
			em.getTransaction().begin();
			String test = "SELECT value FROM ID_SEQUENCE WHERE name = :name FOR UPDATE";
			Map<String, Object> params = new HashMap<>();
			params.put("name", name);
			Query query = em.createNativeQuery(test);
			query.setParameter("name", name);
			rets = query.getResultList();
			Long ret = null;
			if (rets.size() == 0) {
				test = "INSERT INTO ID_SEQUENCE (name, value) VALUES(:name, 1)";
				ret = 1L;
			}
			else {
				test = "UPDATE ID_SEQUENCE SET value = value + 1 WHERE name = :name";
				ret = ((BigInteger)rets.get(0)).longValue() + 1;
			}
			query = em.createNativeQuery(test);
			query.setParameter("name", name);
			query.executeUpdate();
			em.getTransaction().commit();
			return ret;
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
		finally {
			em.close();
		}

	}
}
