package com.juggle.juggle.framework.data.json.load.impl;

import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.data.filter.SimpleQuery;
import com.juggle.juggle.framework.data.json.load.ILoader;
import com.juggle.juggle.framework.data.json.meta.Many;
import com.juggle.juggle.framework.data.json.meta.Many2Many;
import com.juggle.juggle.framework.data.json.meta.One;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;

@Component
public class JPALoader implements ILoader {
	@Autowired
	private SimpleQuery query;
	
	private Object produceJSONObject(Object obj, String refId, String[] fieldset, Class wrapper) {
		Object ret = obj;
		if (null != obj && obj.getClass().isArray()) {
			Map<String, Object> objMap = new HashMap<>();
			Object ref = Array.get(obj, 0);
			objMap.put(refId, ref);
			int i = 1;
			for (String f :fieldset) {
				objMap.put(f, Array.get(obj, i++));
			}
			ret = objMap;
		}

		if (null != wrapper && !Void.class.equals(wrapper)) {
			return JsonUtils.readValue(JsonUtils.writeValue(ret), wrapper);
		}
		else {
			return ret;
		}
	}
	
	@Override
	public Map<Object, Object> loadOneInBatch(Collection<Object> batch, One one) {
		Map<Object, Object> map = new HashMap<>();
		if (batch.isEmpty()) {
			return map;
		}
		
		StringBuffer hql = new StringBuffer();
		
		String fieldset = " a ";
		if (one.fieldset().length > 0) {
			fieldset = one.ref();
			for (String f : one.fieldset()) {
				fieldset += ", " + f;
			}
		}
		String where = " WHERE " + one.ref() + " IN (:refs) ";
		if (one.filter().length() > 0) {
			where = where + one.filter();
		}

		hql.append("SELECT ").append(fieldset).append(" FROM ").append(one.target().getName()).append(" a ").append(where);
		Map<String, Object> params = new HashMap<>();
		params.put("refs", batch);
		
		List<?> ret = query.query(hql.toString(), params);
		
		try {
			for (Object obj : ret) {
				Object jsonObj = produceJSONObject(obj, one.ref(), one.fieldset(), one.wrapper());
				map.put(PropertyUtils.getProperty(jsonObj, one.ref()), jsonObj);
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return map;
	}
	
	@Override
	public Object loadOne(Object value, One one) {
		StringBuffer hql = new StringBuffer();
		
		String fieldset = " a ";
		if (one.fieldset().length > 0) {
			fieldset = one.ref();
			for (String f : one.fieldset()) {
				fieldset += ", " + f;
			}
		}
		String where = " WHERE " + one.ref() + " = :ref ";
		if (one.filter().length() > 0) {
			where = where + one.filter();
		}

		hql.append("SELECT ").append(fieldset).append(" FROM ").append(one.target().getName()).append(" a ").append(where);
		Map<String, Object> params = new HashMap<>();
		params.put("ref", value);
		
		Object obj = query.queryOne(hql.toString(), params);
		return produceJSONObject(obj, one.ref(), one.fieldset(), one.wrapper());
	}

	@Override
	public List<?> loadMany(Object value, Many many) {
		
		StringBuffer hql = new StringBuffer();
		
		String fieldset = " a ";
		if (many.fieldset().length > 0) {
			fieldset = many.ref();
			for (String f : many.fieldset()) {
				fieldset += ", " + f;
			}
		}
		String where = " WHERE " + many.ref() + " = :ref ";
		if (many.filter().length() > 0) {
			where = where + many.filter();
		}

		hql.append("SELECT ").append(fieldset).append(" FROM ").append(many.target().getName()).append(" a ").append(where);
		Map<String, Object> params = new HashMap<>();
		params.put("ref", value);
		
		List<?> list = query.query(hql.toString(), params, 0, many.size());
		
		List ret = new ArrayList<>(list.size());
		for (Object obj : list) {
			ret.add(produceJSONObject(obj, many.ref(), many.fieldset(), many.wrapper()));
		}
		return ret;
	}
	
	@Override
	public List<?> loadMany2Many(Object value, Many2Many m2m) {
		
		StringBuffer hql = new StringBuffer();
		
		String fieldset = " a ";
		if (m2m.fieldset().length > 0) {
			fieldset = "id";
			for (String f : m2m.fieldset()) {
				fieldset += ", " + f;
			}
		}
		
		String where = " WHERE EXISTS (SELECT 1 FROM " + m2m.mediator().getName() + " m WHERE m." + m2m.targetRef()+ "= a.id AND m." + m2m.ref() + " = :ref)";
		if (m2m.filter().length() > 0) {
			where = where + m2m.filter();
		}

		hql.append("SELECT ").append(fieldset).append(" FROM ").append(m2m.target().getName()).append(" a ").append(where);
		Map<String, Object> params = new HashMap<>();
		params.put("ref", value);
		
		List<?> list = query.query(hql.toString(), params, 0, m2m.size());
		
		List ret = new ArrayList<>(list.size());
		for (Object obj : list) {
			ret.add(produceJSONObject(obj, m2m.ref(), m2m.fieldset(), m2m.wrapper()));
		}
		return ret;
	}
}
