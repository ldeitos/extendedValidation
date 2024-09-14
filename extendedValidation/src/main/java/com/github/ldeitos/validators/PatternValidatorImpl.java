package com.github.ldeitos.validators;

import jakarta.validation.ConstraintValidatorContext;

import com.github.ldeitos.constraint.Pattern;
import com.github.ldeitos.constraint.Pattern.Flag;

public class PatternValidatorImpl implements PatternValidator {
	private java.util.regex.Pattern pattern;
	
	public void initialize(Pattern constraintAnnotation) {
		int flagValue = 0;
		
		for(Flag flag : constraintAnnotation.flags()){
			flagValue = flagValue | flag.getValue();
		}
	
		pattern = java.util.regex.Pattern.compile(constraintAnnotation.regexp(), flagValue);
	}
	
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		boolean ret = true;

		if(value != null) {
			ret = pattern.matcher(value).matches();
		}
		
		return ret;
	}


	
}
