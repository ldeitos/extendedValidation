package com.github.ldeitos.test.base.stubs;

import com.github.ldeitos.constraint.NotNull;

public class TestBeanA {

	@NotNull(message = "Invalid TestBeanA field")
	private String field;

	@NotNull(message = "Invalid TestBeanA otherField", groups = GrupoTestBeanA.class)
	private String otherfield;

	public TestBeanA() {
		this(null);
	}

	public TestBeanA(String str) {
		field = str;
	}
}
