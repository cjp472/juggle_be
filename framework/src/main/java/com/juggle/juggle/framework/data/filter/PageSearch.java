package com.juggle.juggle.framework.data.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageSearch implements Serializable {

	private final static int MAX_SIZE = 100;
	private String sort;
	private int size;
	private int page;
	private List<ValueFilter> filters = new ArrayList<>();
	private Map<String, Object> extra;
	
	public Map<String, Object> getExtra() {
		return extra;
	}
	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List<ValueFilter> getFilters() {
		return filters;
	}
	public void setFilters(List<ValueFilter> filters) {
		this.filters = filters;
	}
	public int getSize() {
		if (size == 0) {
			return 10;
		}
		return Math.min(MAX_SIZE, size);
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}