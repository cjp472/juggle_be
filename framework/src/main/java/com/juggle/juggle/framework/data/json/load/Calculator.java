package com.juggle.juggle.framework.data.json.load;

import java.util.Collection;

import com.juggle.juggle.framework.data.json.context.KeyValue;

public interface Calculator<T> {
	Object calc(T bean, Collection<KeyValue> exts);
}
