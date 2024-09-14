package com.github.ldeitos.validators.util;

import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext;

public class NodeBuilderDefinedContextAdapter implements ConstraintBuilderAdapter {
	private NodeBuilderDefinedContext nBuilder;

	private NodeBuilderCustomizableContext nbCustonCtxt;

	public NodeBuilderDefinedContextAdapter(NodeBuilderDefinedContext nBuilder) {
		this.nBuilder = nBuilder;
	}

	@Override
	public ConstraintBuilderAdapter addPropertyNode(Path path) {
		nbCustonCtxt = nBuilder.addPropertyNode(path.getPath());
		return new NodeBuilderCustomizableContextAdapter(nbCustonCtxt);
	}

	@Override
	public void addConstraintViolation() {
		if (nbCustonCtxt != null) {
			nbCustonCtxt.addConstraintViolation();
		}
	}

}
