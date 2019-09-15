package com.juggle.juggle.framework.http.resp.impl;


import com.juggle.juggle.framework.http.resp.SyncResponseHandler;

public class TextResponseHandler extends SyncResponseHandler<String> {

	@Override
	public String parse(String content) {
		return content;
	}
}
