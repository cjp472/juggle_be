package com.juggle.juggle.framework.http.resp;

import java.io.IOException;

import org.apache.http.HttpEntity;


public interface IResponseHandler<T> {
	void setDebug(boolean debug);
	void onSuccess(T content);
	void onFailure(int statusCode, byte[] content);
	
	T handle(HttpEntity entity) throws IOException ;
}
