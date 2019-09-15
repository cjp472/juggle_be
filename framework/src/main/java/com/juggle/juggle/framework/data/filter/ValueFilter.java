package com.juggle.juggle.framework.data.filter;

public class ValueFilter {

	public final static String OP_EQ = "=";
	public final static String OP_NEQ = "<>";
	public final static String OP_LE = "<=";
	public final static String OP_GE = ">=";
	public final static String OP_LT = "<";
	public final static String OP_GT = ">";
	public final static String OP_LIKE = "*";
	public final static String OP_LEFT_LIKE = "%";
	public final static String OP_IN = "in";
	public final static String OP_IS_NULL = "is_null";
	
	private String name;
	private String op;
	private Object value;
	
	public ValueFilter() {}
	
	public ValueFilter(String name, String op, Object value) {
		super();
		this.name = name;
		this.op = op;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
