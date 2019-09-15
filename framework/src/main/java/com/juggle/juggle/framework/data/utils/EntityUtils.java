package com.juggle.juggle.framework.data.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.juggle.juggle.framework.data.entity.BaseEntity;

public class EntityUtils {
	public static Collection retriveLongIds(Collection<? extends BaseEntity> col) {
		Collection ret = new ArrayList<>();
		for (BaseEntity entity : col) {
			ret.add(entity.getId());
		}
		return ret;
	}
}
