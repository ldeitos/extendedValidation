package com.github.ldeitos.validators;

import java.math.BigDecimal;

import com.github.ldeitos.constraint.DecimalMin;

public class MinDecimalValidatorImpl extends BigDecimalComparativeValidator<DecimalMin> {
	private BigDecimal minValue;
	
	private boolean inclusive;

	public void initialize(DecimalMin constraintAnnotation) {
		minValue = new BigDecimal(constraintAnnotation.value());
		inclusive = constraintAnnotation.inclusive();
	}

	@Override
	protected boolean compareValid(BigDecimal n) {
		int comparassion = minValue.compareTo(n);
		return inclusive ? comparassion <= 0 : comparassion < 0;
	}

}
