package com.github.ldeitos.validator.stubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PathTestConstraint(value = "valueParametrized")
public class PathBean {
	private String stringField;

	private Map<String, PathComposeBean> map = new HashMap<String, PathComposeBean>();

	private List<PathComposeBean> list = new ArrayList<PathComposeBean>();

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String field) {
		stringField = field;
	}

	public Map<String, PathComposeBean> getMap() {
		return map;
	}

	public void setMap(Map<String, PathComposeBean> map) {
		this.map = map;
	}

	public List<PathComposeBean> getList() {
		return list;
	}

	public void setList(List<PathComposeBean> list) {
		this.list = list;
	}
}
