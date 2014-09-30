package com.github.ldeitos.validation.impl;

import org.junit.Test;

import com.github.ldeitos.exception.InvalidConfigurationException;

public class InvalidConfigurationExceptionTest {
	
	@Test(expected = InvalidConfigurationException.class)
	public void testThrowMSG() {
		InvalidConfigurationException.throwNew("Teste");		
	}
	
	@Test(expected = InvalidConfigurationException.class)
	public void testThrowMSGAndCause() {
		InvalidConfigurationException.throwNew("Teste", new RuntimeException());		
	}
}
