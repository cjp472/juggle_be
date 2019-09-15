package com.juggle.juggle.framework.http.resp;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

public abstract class StringResponseHandler implements IResponseHandler<String> {
	protected boolean isDebug;

	@Override
	public void setDebug(boolean debug) {
		this.isDebug = debug;		
	}

	@Override
	public String handle(HttpEntity entity) throws IOException {
		
		return EntityUtils.toString(entity);
	}
}
