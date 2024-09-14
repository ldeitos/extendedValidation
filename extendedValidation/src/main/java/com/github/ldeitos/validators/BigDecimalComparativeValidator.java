package com.github.ldeitos.validators;

import static java.lang.String.format;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidParameterException;

abstract class BigDecimalComparativeValidator<A extends Annotation> extends MultiTargetValidator<A> {
	private final Class<?>[] targetClasses = {Number.class, CharSequence.class}; 

	protected boolean doValidation(Object value) {
		BigDecimal n;
		Class<?> valueClass = value.getClass();
		if(BigDecimal.class.isAssignableFrom(valueClass)){
			n = BigDecimal.class.cast(value);
		} else if(BigInteger.class.isAssignableFrom(valueClass)){
			n = new BigDecimal(BigInteger.class.cast(value));
		} else if(CharSequence.class.isAssignableFrom(valueClass)){
			try {
				n = new BigDecimal(value.toString());
			} catch (NumberFormatException e){
				throw new InvalidParameterException(
					format("Value [%s] invalid to be validated by [%s] validator.", value, 
						getClass().getName()));
			}
		} else if(Long.class.isAssignableFrom(valueClass)){
			n = new BigDecimal(Long.class.cast(value));
		} else {
			n = new BigDecimal(Number.class.cast(value).doubleValue());
		}		
		
		return compareValid(n);
	}
	
	@Override
	protected Class<?>[] getTargetClasses() {
		return targetClasses;
	}

	protected abstract boolean compareValid(BigDecimal n);
}
