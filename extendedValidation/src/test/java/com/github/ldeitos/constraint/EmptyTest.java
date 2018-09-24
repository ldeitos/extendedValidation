package com.github.ldeitos.constraint;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;

import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;
import com.github.ldeitos.validators.EmptyValidatorImpl;
import com.github.ldeitos.validators.SizeValidatorImpl;

@AdditionalClasses({ TestMessageSource.class, EmptyValidatorImpl.class })
public class EmptyTest extends BaseTest {

	private static final String MENSAGEM_ESPERADA = "Empty Teste";

	@Produces
	@ProducesAlternative
	private ConfigInfoProvider cip = new ConfigInfoProvider() {
		@Override
		protected boolean isInTest() {
			return true;
		}
	};

	@Inject
	@ExtendedValidator
	private Validator validador;

	@Test
	public void testPrimitiveArrayNull() {
		TestePrimitiveArray var = new TestePrimitiveArray();
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayValid() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] {});
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayInvalid() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] { 1, 2, 3, 4, 5 });
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCollectionNull() {
		TesteCollection var = new TesteCollection();
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionValid() {
		TesteCollection var = new TesteCollection(new ArrayList<String>());
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionInvalid() {
		TesteCollection var = new TesteCollection(asList(true));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMapNull() {
		TesteMap var = new TesteMap();
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapValid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapInvalid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCharSequenceNull() {
		TesteCharSequence var = new TesteCharSequence();
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceValid() {
		TesteCharSequence var = new TesteCharSequence("");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceInvalid() {
		TesteCharSequence var = new TesteCharSequence("12345");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test(expected = ValidationException.class)
	public void testInvalidType() {
		TesteInvalidType var = new TesteInvalidType();
		validador.validate(var);
	}

	static class TesteInvalidType {
		@Empty(messageParameters = { "par=Teste" })
		private Integer campo = 1;
	}

	static class TesteCollection {
		@Empty(messageParameters = { "par=Teste" })
		private Collection<?> campo;

		TesteCollection() {
		}

		TesteCollection(Collection<?> val) {
			campo = val;
		}
	}

	static class TesteMap {
		@Empty(messageParameters = { "par=Teste" })
		private Map<?, ?> campo;

		TesteMap() {
		}

		TesteMap(Map<?, ?> val) {
			campo = val;
		}
	}

	static class TesteCharSequence {
		@Empty(messageParameters = { "par=Teste" })
		private CharSequence campo;

		TesteCharSequence() {
		}

		TesteCharSequence(CharSequence val) {
			campo = val;
		}
	}

	static class TestePrimitiveArray {
		@Empty(messageParameters = { "par=Teste" })
		private int[] campo;

		TestePrimitiveArray() {
		}

		TestePrimitiveArray(int[] val) {
			campo = val;
		}
	}

	@Override
	protected Class<?> getClassOnTest() {
		return SizeValidatorImpl.class;
	}
}