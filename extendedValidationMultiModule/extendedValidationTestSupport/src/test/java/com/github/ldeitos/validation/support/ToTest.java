package com.github.ldeitos.validation.support;

import com.github.ldeitos.constraint.NotNull;
import com.github.ldeitos.constraint.Size;

public class ToTest {

	@NotNull(message = "Must be not null")
	private String field;

	@Size(min = 5, message = "Must have minimum lentgh size equals 5")
	private String otherField;

	public ToTest() {
		this(null, null);
	}

	public ToTest(String otherField) {
		this(null, otherField);
	}

	public ToTest(String field, String otherField) {
		this.field = field;
		this.otherField = otherField;
	}

}
