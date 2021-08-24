package com.github.ldeitos.validation.impl.util;

import static org.junit.Assert.assertEquals;

import javax.enterprise.inject.Produces;

import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Ignore;
import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validation.impl.configuration.ConfigInfo;

@Ignore
public class PresentationMessageFormatterTest extends BaseTest{
	
	@Produces
	@ProducesAlternative
	private ConfigInfo cip = new ConfigInfo() {
		@Override
		public String getConfigFileName() {
			return "META-INF/extendedValidation_configuredTemplateMessagePresentation.xml";
		}

		@Override
		protected boolean isInTest() {
			return true;
		}
	};

	private static final String TEMPLATE = "{ER000}";
	private static final String TEMPLATE_DOLAR = "{$$$}";

	@Test
	public void testFormatComDolarNaMensagem() {
		assertEquals("(ER000) Erro: testando o dolar $ na mensagem.",
				PresentationMessageFormatter.format(TEMPLATE, "Erro: testando o dolar $ na mensagem."));
	}

	@Test
	public void testFormatComDolarNaChaveDaMensagem() {
		assertEquals("($$$) Erro: testando o dolar na chave da mensagem.",
				PresentationMessageFormatter.format(TEMPLATE_DOLAR, "Erro: testando o dolar na chave da mensagem."));
	}

}
