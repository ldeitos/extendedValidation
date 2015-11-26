package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.constraint.NotNull;

public class TestBeanC {

	@NotNull(message = "{ER001}", messageParameters = "{par1}")
	private String field;

	public TestBeanC() {
		this(null);
	}

	public TestBeanC(String str) {
		field = str;
	}
}
