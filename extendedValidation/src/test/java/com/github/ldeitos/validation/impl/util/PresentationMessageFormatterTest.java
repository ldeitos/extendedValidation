package com.github.ldeitos.validation.impl.util;

import static org.junit.Assert.assertEquals;

import jakarta.enterprise.inject.Produces;

import io.github.cdiunit.CdiRunner;
import io.github.cdiunit.ProducesAlternative;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;

@RunWith(CdiRunner.class)
@Ignore
public class PresentationMessageFormatterTest {

	@Produces
	@ProducesAlternative
	private ConfigInfoProvider cip = new ConfigInfoProvider() {
		@Override
		public String getConfigFileName() {
			return "extendedValidation_configuredTemplateMessagePresentation.xml";
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
