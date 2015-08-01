package com.github.ldeitos.validators.util;

import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext;

public class NodeBuilderDefinedContextAdapter implements ConstraintBuilderAdapter {
	private NodeBuilderDefinedContext nBuilder;

	private NodeBuilderCustomizableContext nbCustonCtxt;

	public NodeBuilderDefinedContextAdapter(NodeBuilderDefinedContext nBuilder) {
		this.nBuilder = nBuilder;
	}

	@Override
	public ConstraintBuilderAdapter addPropertyNode(Path path) {
		ConstraintBuilderAdapter result;
		nbCustonCtxt = nBuilder.addNode(path.getPath());

		if (path.isIterable()) {
			result = new IterablePropertyNodeContextAdapter(nbCustonCtxt).addPropertyNode(path);
		} else {
			result = new NodeBuilderCustomizableContextAdapter(nbCustonCtxt);
		}

		return result;
	}

	@Override
	public void addConstraintViolation() {
		if (nbCustonCtxt != null) {
			nbCustonCtxt.addConstraintViolation();
		} else {
			nBuilder.addConstraintViolation();
		}
	}

}
