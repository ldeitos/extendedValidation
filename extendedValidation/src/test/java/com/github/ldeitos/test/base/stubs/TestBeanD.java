package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.constraint.NotNull;

public class TestBeanD {

	@NotNull(message = "{ER002}", messageParameters = "field={par1}")
	private String field;

	public TestBeanD() {
		this(null);
	}

	public TestBeanD(String str) {
		field = str;
	}
}
