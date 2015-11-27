package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.constraint.NotNull;

public class TestBeanF {

	@NotNull(message = "{ER002}", messageParameters = "field=Parametrized")
	private String field;

	public TestBeanF() {
		this(null);
	}

	public TestBeanF(String str) {
		field = str;
	}
}
