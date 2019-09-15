package com.juggle.juggle.framework.data.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageResult<T> {
	private List<T> rows;
	private int size;
	private int page;
	private long total;
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}	
	
	public static <T> PageResult<T> empty() {
		PageResult<T> data = new PageResult<>();
		data.setRows(new ArrayList<>());
		return data;
	}
	
	public static <T> PageResult<T> from(Page<T> page)
	{
		PageResult<T> data = new PageResult<>();
		data.setTotal(page.getTotalElements());
		data.setRows(page.getContent());
		data.setSize(page.getSize());
		data.setPage(page.getNumber());
		return data;
	}
}
