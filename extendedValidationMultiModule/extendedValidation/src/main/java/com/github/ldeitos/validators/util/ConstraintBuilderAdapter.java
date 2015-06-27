package com.github.ldeitos.validators.util;

public interface ConstraintBuilderAdapter {
	ConstraintBuilderAdapter addPropertyNode(Path path);

	void addConstraintViolation();
}
