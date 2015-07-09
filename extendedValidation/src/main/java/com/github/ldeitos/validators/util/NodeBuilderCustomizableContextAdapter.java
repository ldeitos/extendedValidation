package com.github.ldeitos.validators.util;

import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

public class NodeBuilderCustomizableContextAdapter implements ConstraintBuilderAdapter {
	private NodeBuilderCustomizableContext nBuilder;

	public NodeBuilderCustomizableContextAdapter(NodeBuilderCustomizableContext nBuilder) {
		this.nBuilder = nBuilder;
	}

	@Override
	public ConstraintBuilderAdapter addPropertyNode(Path path) {
		ConstraintBuilderAdapter result;
		nBuilder = nBuilder.addPropertyNode(path.getPath());

		if (path.isIterable()) {
			result = new IterablePropertyNodeContextAdapter(nBuilder).addPropertyNode(path);
		} else {
			result = new NodeBuilderCustomizableContextAdapter(nBuilder);
		}

		return result;
	}

	@Override
	public void addConstraintViolation() {
		if (nBuilder != null) {
			nBuilder.addConstraintViolation();
		}
	}
}
