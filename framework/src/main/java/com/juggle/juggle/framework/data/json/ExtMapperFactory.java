package com.juggle.juggle.framework.data.json;

import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.data.json.modify.ExtBeanSerializerModifier;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExtMapperFactory {
	public static ObjectMapper create() {
		ObjectMapper mapper = JsonUtils.createMapper();
		mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new ExtBeanSerializerModifier()));
		return mapper;
	}
}
