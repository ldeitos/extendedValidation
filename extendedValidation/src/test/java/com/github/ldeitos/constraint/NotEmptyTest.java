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

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.jglue.cdiunit.AdditionalClasses;
import org.junit.Test;

import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;
import com.github.ldeitos.validators.NotEmptyValidatorImpl;
import com.github.ldeitos.validators.SizeValidatorImpl;

@AdditionalClasses({ TestMessageSource.class, NotEmptyValidatorImpl.class })
public class NotEmptyTest extends BaseTest {

	private static final String MENSAGEM_ESPERADA = "NotEmpty Teste";

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
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] { 1 });
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayInvalid() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] {});
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
		TesteCollection var = new TesteCollection(asList(true));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionInvalid() {
		TesteCollection var = new TesteCollection(new ArrayList<String>());
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
		map.put(1, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapInvalid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
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
		TesteCharSequence var = new TesteCharSequence("1");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceInvalid() {
		TesteCharSequence var = new TesteCharSequence("");
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
		@NotEmpty(messageParameters = { "par=Teste" })
		private Integer campo = 1;
	}

	static class TesteCollection {
		@NotEmpty(messageParameters = { "par=Teste" })
		private Collection<?> campo;

		TesteCollection() {
		}

		TesteCollection(Collection<?> val) {
			campo = val;
		}
	}

	static class TesteMap {
		@NotEmpty(messageParameters = { "par=Teste" })
		private Map<?, ?> campo;

		TesteMap() {
		}

		TesteMap(Map<?, ?> val) {
			campo = val;
		}
	}

	static class TesteCharSequence {
		@NotEmpty(messageParameters = { "par=Teste" })
		private CharSequence campo;

		TesteCharSequence() {
		}

		TesteCharSequence(CharSequence val) {
			campo = val;
		}
	}

	static class TestePrimitiveArray {
		@NotEmpty(messageParameters = { "par=Teste" })
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
