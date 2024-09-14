package com.github.ldeitos.validators;

import java.math.BigDecimal;

import com.github.ldeitos.constraint.DecimalMax;

public class MaxDecimalValidatorImpl extends BigDecimalComparativeValidator<DecimalMax> 
	implements MaxDecimalValidator {
	private BigDecimal maxValue;
	
	private boolean inclusive;

	public void initialize(DecimalMax constraintAnnotation) {
		maxValue = new BigDecimal(constraintAnnotation.value());
		inclusive = constraintAnnotation.inclusive();
	}

	@Override
	protected boolean compareValid(BigDecimal n) {
		int comparassion = maxValue.compareTo(n);
		return inclusive ? comparassion >= 0 : comparassion > 0;
	}

}
