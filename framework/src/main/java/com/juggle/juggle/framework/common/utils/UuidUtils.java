package com.juggle.juggle.framework.common.utils;

import java.util.UUID;

public class UuidUtils {
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}
}
