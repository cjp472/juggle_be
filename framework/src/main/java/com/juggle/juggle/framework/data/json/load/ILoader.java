package com.juggle.juggle.framework.data.json.load;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.juggle.juggle.framework.data.json.meta.Many;
import com.juggle.juggle.framework.data.json.meta.Many2Many;
import com.juggle.juggle.framework.data.json.meta.One;

public interface ILoader {
	Object loadOne(Object value, One one);
	List<?> loadMany(Object value, Many many);
	Map<Object, Object> loadOneInBatch(Collection<Object> batch, One one);
	public List<?> loadMany2Many(Object value, Many2Many m2m);
}
