package com.github.ldeitos.validators;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.ldeitos.constraint.Min;

public class MinValidatorImpl extends  NumberComparativeValidator<Min> 
	implements MinValidator {
	
	private long min;
			
	public void initialize(Min constraintAnnotation) {
		this.min = constraintAnnotation.value();
	}


	@Override
	protected boolean compareValid(Number n) {
		boolean get = true;
		Class<? extends Number> numberClass = n.getClass();

		if(BigDecimal.class.isAssignableFrom(numberClass)){
			BigDecimal value = BigDecimal.class.cast(n);
			get = value.compareTo(BigDecimal.valueOf(min)) >= 0;
		} else if(BigInteger.class.isAssignableFrom(numberClass)){
			BigInteger value = BigInteger.class.cast(n);
			get = value.compareTo(BigInteger.valueOf(min)) >= 0;
		} else {
			long value =  n.longValue();
			get = value >= min;
		}
		
		return get;
	}

}
