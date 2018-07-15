package com.github.ldeitos.validation.impl.interpolator;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;

public class PreInterpolatorTest extends BaseTest {
	private static final String MSG_KEY = "Mensagem: {par1}, {par2}, {par3} e {value}";
	
	@Inject
	private PreInterpolator preInterpolator;

	@Test
	public void testFormatedOutput() {
		assertEquals("Mensagem: parametro com '=' no texto, "
				+ "parametro com ':' no texto, parametro com !@#$ e parametro com ':' e '=' no texto", 
				preInterpolator.interpolate(MSG_KEY, "par1=parametro com '=' no texto", 
				"par2=parametro com ':' no texto", "value=parametro com ':' e '=' no texto", "par3=parametro com !@#$" ));
	}


}