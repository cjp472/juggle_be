package com.juggle.juggle.framework.data.service;

import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;

import java.util.List;

public interface ICRUDService<T> {
	T create(Object data);
	T save(T data);
	T find(Long id);
	T update(Long id, Object data);
	void delete(Long id);
	List<T> readAll();
	PageResult<T> search(PageSearch ps);
}
