package com.juggle.juggle.framework.event;


public interface IEventDispatcher
{
	void dispatch(Event event);
    void addListener(String code, IEventListener listener);
	void removeListener(String code, IEventListener listener);
    void addCallback(String code, IEventCallback callback);
	void removeCallback(String code, IEventCallback listener);
}
