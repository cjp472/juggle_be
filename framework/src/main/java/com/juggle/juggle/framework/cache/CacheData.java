package com.juggle.juggle.framework.cache;

import java.io.Serializable;

import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CacheData<C> implements Serializable {
	protected static ObjectMapper MAPPER = JsonUtils.createMapper();
	private C data;
	public CacheData() {}
	public CacheData(C data) {
		this.data = data;
	}
	public C getData() {
		return data;
	}
	public void setData(C data) {
		this.data = data;
	}
	
	public byte[] toJson() {
		try {
			return MAPPER.writeValueAsBytes(this);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static CacheData fromJson(byte[] value, Class clazz) {
		JavaType type = MAPPER.getTypeFactory().constructParametricType(CacheData.class, clazz);
        try
		{
        	CacheData c;
        	if (null == value) {
        		c = new CacheData();
        	}
        	else {
            	c = MAPPER.readValue(value, type);
        	}
        	return c;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
}