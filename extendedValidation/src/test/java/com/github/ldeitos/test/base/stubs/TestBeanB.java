package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.constraint.NotNull;

public class TestBeanB {

	@NotNull(message = "Invalid TestBeanB field")
	private String field;

	public TestBeanB() {
		this(null);
	}

	public TestBeanB(String str) {
		field = str;
	}
}
