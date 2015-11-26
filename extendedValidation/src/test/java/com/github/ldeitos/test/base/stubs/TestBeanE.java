package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.constraint.NotNull;

public class TestBeanE {

	@NotNull(message = "{ER001}", messageParameters = "Parametrized")
	private String field;

	public TestBeanE() {
		this(null);
	}

	public TestBeanE(String str) {
		field = str;
	}
}
