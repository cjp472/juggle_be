package com.juggle.juggle.framework.common.export.csv;

import java.util.List;

public interface CsvSource<T> {
	String[] getHeaders();
	List<T> list();
	Object getData(int row, int col, String header);
}
