package com.github.ldeitos.validators;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import com.github.ldeitos.constraint.Digits;

public class DigitsValidatorImpl extends BigDecimalComparativeValidator<Digits> 
	implements DigitsValidator {
	private int integerPart;
	
	private int fractionPart;
	
	public void initialize(Digits constraintAnnotation) {
		integerPart = validateParam(constraintAnnotation.integer(), "integer length");
		fractionPart = validateParam(constraintAnnotation.fraction(), "fraction length");
	}

	private int validateParam(int value, String param) {
		
		if(value < 0) {
			throw new InvalidParameterException(format("Invalid %s: [%d]", param, value));
		}
		
		return value;
	}

	@Override
	protected boolean compareValid(BigDecimal n) {
		int integerPartLength = n.precision() - n.scale();
		int fractionPartLength = n.scale() < 0 ? 0 : n.scale();

		return ( integerPart >= integerPartLength && fractionPart >= fractionPartLength );
	}

}
