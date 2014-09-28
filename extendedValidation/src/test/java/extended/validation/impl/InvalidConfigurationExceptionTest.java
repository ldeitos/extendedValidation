package extended.validation.impl;

import org.junit.Test;

import extended.exception.InvalidConfigurationException;

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
