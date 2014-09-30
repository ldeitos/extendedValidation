package com.github.ldeitos.validation;

import javax.validation.Payload;

/**
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class ConstraintSeverity implements Payload {
	private class ERROR extends ConstraintSeverity {};
	private class INFO extends ConstraintSeverity {};
	private class WARN extends ConstraintSeverity {};
	private class FATAL extends ConstraintSeverity {};
	
	public static Class<? extends ConstraintSeverity> error(){
		return ERROR.class;
	}
	
	public static Class<? extends ConstraintSeverity> info(){
		return INFO.class;
	}
	
	public static  Class<? extends ConstraintSeverity> warn(){
		return WARN.class;
	}
	
	public static  Class<? extends ConstraintSeverity> fatal(){
		return FATAL.class;
	}
	
	public static Class<? extends ConstraintSeverity> defaultValue(){
		return error();
	}
}
