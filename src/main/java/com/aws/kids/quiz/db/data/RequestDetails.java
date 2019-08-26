package com.aws.kids.quiz.db.data;

import java.util.Map;

public class RequestDetails {
	private String name;
	private String method;
	private Map<String, Object> values;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, Object> getValues() {
		return values;
	}
	public void setValues(Map<String, Object> values) {
		this.values = values;
	}
	
	
}