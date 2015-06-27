package com.github.ldeitos.validators.util;

import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext;

public class IterablePropertyNodeContextAdapter implements ConstraintBuilderAdapter {
	private NodeBuilderCustomizableContext propertyNodeBuilder;

	private NodeBuilderDefinedContext nBuilder;

	public IterablePropertyNodeContextAdapter(NodeBuilderCustomizableContext propertyNodeBuilder) {
		this.propertyNodeBuilder = propertyNodeBuilder;
	}

	@Override
	public ConstraintBuilderAdapter addPropertyNode(Path path) {
		if (path.hasKey()) {
			nBuilder = propertyNodeBuilder.inIterable().atKey(path.getKey());
		} else {
			nBuilder = propertyNodeBuilder.inIterable().atIndex(path.getIndex());
		}

		return new NodeBuilderDefinedContextAdapter(nBuilder);
	}

	@Override
	public void addConstraintViolation() {
		if (nBuilder != null) {
			nBuilder.addConstraintViolation();
		}
	}

}
