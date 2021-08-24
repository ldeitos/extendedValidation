package com.github.ldeitos.validation.impl.interpolator;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;

import java.util.Locale;

import static com.github.ldeitos.test.base.GeneralTestConfiguration.ENABLE_REAL_IMPLEMETATION;

import org.junit.Ignore;

public class PreInterpolatorTest extends BaseTest {
	private static final String MSG_KEY = "Mensagem: {par1}, {par2}, {par3} e {value}";
	private static final String DEFAULT_LOCALIZED_MESSAGE = "Localized default file message.";
	
	@Inject
	private PreInterpolator preInterpolator;

	@Test
	public void testFormatedOutput() {
		assertEquals("Mensagem: parametro com '=' no texto, "
				+ "parametro com ':' no texto, parametro com !@#$ e parametro com ':' e '=' no texto", 
				preInterpolator.interpolate(MSG_KEY, "par1=parametro com '=' no texto", 
				"par2=parametro com ':' no texto", "value=parametro com ':' e '=' no texto", "par3=parametro com !@#$" ));
	}

	@Test
	@Ignore("Test fail in CI environment")
	public void testFormatedOutputi18n() {
		ENABLE_REAL_IMPLEMETATION = true;
		Locale locale = new Locale("es_ES");
		String msg = preInterpolator.interpolate("{validation.default.test}", locale, "");
		assertEquals(DEFAULT_LOCALIZED_MESSAGE, msg);
	}	

}
