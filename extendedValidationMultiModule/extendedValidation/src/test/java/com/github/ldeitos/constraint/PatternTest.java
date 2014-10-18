package com.github.ldeitos.constraint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.jglue.cdiunit.AdditionalClasses;
import org.junit.Test;

import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;
import com.github.ldeitos.validators.PatternValidatorImpl;

@AdditionalClasses({ TestMessageSource.class, PatternValidatorImpl.class })
public class PatternTest extends BaseTest {

	private static final String MENSAGEM_ESPERADA = "Pattern Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;

	@Test
	public void testPatterValid() {
		Teste var = new Teste("{x=y}");
		Set<ConstraintViolation<Teste>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPatterValid2() {
		Teste var = new Teste("{xy}");
		Set<ConstraintViolation<Teste>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPatterValid3() {
		Teste var = new Teste("{fadfx=yfsda]");
		Set<ConstraintViolation<Teste>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPatterInvalid() {
		Teste var = new Teste("teste");
		Set<ConstraintViolation<Teste>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	static class Teste {
		@Pattern(messageParameters = { "par=Teste" }, regexp = "^(\\{|\\[)(.*)(=)?(.*)(\\]|\\})$")
		private String campo;

		Teste() {
		}

		Teste(String val) {
			campo = val;
		}
	}

	@Override
	protected Class<?> getClassOnTest() {
		return PatternValidatorImpl.class;
	}
}
