package com.github.ldeitos.validators;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.ldeitos.constraint.Min;

public class MinValidatorImpl extends  NumberComparativeValidator<Min> {
	
	private long min;
			
	public void initialize(Min constraintAnnotation) {
		this.min = constraintAnnotation.value();
	}


	@Override
	protected boolean compareValid(Number n) {
		boolean get = true;
		
		if(n.getClass().isAssignableFrom(BigDecimal.class)){
			BigDecimal value = (BigDecimal) n;
			get = value.compareTo(BigDecimal.valueOf(min)) >= 0;
		} else if(n.getClass().isAssignableFrom(BigInteger.class)){
			BigInteger value = (BigInteger) n;
			get = value.compareTo(BigInteger.valueOf(min)) >= 0;
		} else {
			long value =  n.longValue();
			get = value >= min;
		}
		
		return get;
	}

}
