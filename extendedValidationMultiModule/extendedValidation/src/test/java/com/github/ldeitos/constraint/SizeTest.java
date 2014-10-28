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
import com.github.ldeitos.validators.SizeValidatorImpl;

@AdditionalClasses({ TestMessageSource.class, SizeValidatorImpl.class })
public class SizeTest extends BaseTest {

	private static final String MENSAGEM_ESPERADA = "Size Teste";

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
	public void testPrimitiveArrayValidBottomLimit() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] { 1, 2 });
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayValidTopLimit() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] { 1, 2, 3, 4 });
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayBottomInvalid() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] {});
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testPrimitiveArrayTopInvalid() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] { 1, 2, 3, 4, 5 });
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testPrimitiveArrayMinOnlyBottomValid() {
		TestePrimitiveArrayMinOnly var = new TestePrimitiveArrayMinOnly(new int[] { 1, 2 });
		Set<ConstraintViolation<TestePrimitiveArrayMinOnly>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayMinOnlyBottomInvalid() {
		TestePrimitiveArrayMinOnly var = new TestePrimitiveArrayMinOnly(new int[] {});
		Set<ConstraintViolation<TestePrimitiveArrayMinOnly>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testPrimitiveArrayMaxOnlyTopValid() {
		TestePrimitiveArrayMaxOnly var = new TestePrimitiveArrayMaxOnly(new int[] { 2, 3, 4, 5 });
		Set<ConstraintViolation<TestePrimitiveArrayMaxOnly>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayMaxOnlyTopInvalid() {
		TestePrimitiveArrayMaxOnly var = new TestePrimitiveArrayMaxOnly(new int[] { 1, 2, 3, 4, 5 });
		Set<ConstraintViolation<TestePrimitiveArrayMaxOnly>> violacoes = validador.validate(var);
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
	public void testCollectionValidBottomLimit() {
		TesteCollection var = new TesteCollection(asList(true, false));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionValidTopLimit() {
		TesteCollection var = new TesteCollection(asList(true, false, true, false));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionBottomInvalid() {
		TesteCollection var = new TesteCollection(new ArrayList<String>());
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCollectionTopInvalid() {
		TesteCollection var = new TesteCollection(asList(true, true, false, true, false));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCollectionMaxOnlyValidTopLimit() {
		TesteCollectionMaxOnly var = new TesteCollectionMaxOnly(asList(true, false, true, false));
		Set<ConstraintViolation<TesteCollectionMaxOnly>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionMaxOnlyTopInvalid() {
		TesteCollectionMaxOnly var = new TesteCollectionMaxOnly(asList(true, false, true, true, false));
		Set<ConstraintViolation<TesteCollectionMaxOnly>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCollectionMinOnlyValidBottomLimit() {
		TesteCollectionMinOnly var = new TesteCollectionMinOnly(asList(true, false));
		Set<ConstraintViolation<TesteCollectionMinOnly>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionMinOnlyBottomInvalid() {
		TesteCollectionMinOnly var = new TesteCollectionMinOnly(asList(true));
		Set<ConstraintViolation<TesteCollectionMinOnly>> violacoes = validador.validate(var);
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
	public void testMapValidBottomLimit() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapValidTopLimit() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 1);
		map.put(3, 1);
		map.put(4, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapValidBottomInvalid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMapTopInvalid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 1);
		map.put(3, 1);
		map.put(4, 1);
		map.put(5, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMapMaxOnlyValidTopLimit() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 1);
		TesteMapMaxOnly var = new TesteMapMaxOnly(map);
		Set<ConstraintViolation<TesteMapMaxOnly>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapMaxOnlyTopInvalid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 1);
		map.put(3, 1);
		map.put(4, 1);
		map.put(5, 1);
		TesteMapMaxOnly var = new TesteMapMaxOnly(map);
		Set<ConstraintViolation<TesteMapMaxOnly>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMapMinOnlyValidBottomLimit() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 1);
		TesteMapMinOnly var = new TesteMapMinOnly(map);
		Set<ConstraintViolation<TesteMapMinOnly>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapMinOnlyBottomInvalid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		TesteMapMinOnly var = new TesteMapMinOnly(map);
		Set<ConstraintViolation<TesteMapMinOnly>> violacoes = validador.validate(var);
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
	public void testCharSequenceValidBottomLimit() {
		TesteCharSequence var = new TesteCharSequence("12");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceValidTopLimit() {
		TesteCharSequence var = new TesteCharSequence("1234");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceMinOnlyBottomLimit() {
		TesteCharSequenceMinOnly var = new TesteCharSequenceMinOnly("12");
		Set<ConstraintViolation<TesteCharSequenceMinOnly>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceMinOnlyTopInvalid() {
		TesteCharSequenceMinOnly var = new TesteCharSequenceMinOnly("1");
		Set<ConstraintViolation<TesteCharSequenceMinOnly>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCharSequenceMaxOnlyTopLimit() {
		TesteCharSequenceMaxOnly var = new TesteCharSequenceMaxOnly("1234");
		Set<ConstraintViolation<TesteCharSequenceMaxOnly>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceMaxOnlyTopInvalid() {
		TesteCharSequenceMaxOnly var = new TesteCharSequenceMaxOnly("12345");
		Set<ConstraintViolation<TesteCharSequenceMaxOnly>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCharSequenceBottomInvalid() {
		TesteCharSequence var = new TesteCharSequence("");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCharSequenceTopInvalid() {
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
		@Size(messageParameters = { "par=Teste" }, min = 2, max = 4)
		private Integer campo = 1;
	}

	static class TesteCollection {
		@Size(messageParameters = { "par=Teste" }, min = 2, max = 4)
		private Collection<?> campo;

		TesteCollection() {
		}

		TesteCollection(Collection<?> val) {
			campo = val;
		}
	}

	static class TesteCollectionMinOnly {
		@Size(messageParameters = { "par=Teste" }, min = 2)
		private Collection<?> campo;

		TesteCollectionMinOnly() {
		}

		TesteCollectionMinOnly(Collection<?> val) {
			campo = val;
		}
	}

	static class TesteCollectionMaxOnly {
		@Size(messageParameters = { "par=Teste" }, max = 4)
		private Collection<?> campo;

		TesteCollectionMaxOnly() {
		}

		TesteCollectionMaxOnly(Collection<?> val) {
			campo = val;
		}
	}

	static class TesteMap {
		@Size(messageParameters = { "par=Teste" }, min = 2, max = 4)
		private Map<?, ?> campo;

		TesteMap() {
		}

		TesteMap(Map<?, ?> val) {
			campo = val;
		}
	}

	static class TesteMapMinOnly {
		@Size(messageParameters = { "par=Teste" }, min = 2)
		private Map<?, ?> campo;

		TesteMapMinOnly() {
		}

		TesteMapMinOnly(Map<?, ?> val) {
			campo = val;
		}
	}

	static class TesteMapMaxOnly {
		@Size(messageParameters = { "par=Teste" }, max = 2)
		private Map<?, ?> campo;

		TesteMapMaxOnly() {
		}

		TesteMapMaxOnly(Map<?, ?> val) {
			campo = val;
		}
	}

	static class TesteCharSequence {
		@Size(messageParameters = { "par=Teste" }, min = 2, max = 4)
		private CharSequence campo;

		TesteCharSequence() {
		}

		TesteCharSequence(CharSequence val) {
			campo = val;
		}
	}

	static class TesteCharSequenceMaxOnly {
		@Size(messageParameters = { "par=Teste" }, max = 4)
		private CharSequence campo;

		TesteCharSequenceMaxOnly() {
		}

		TesteCharSequenceMaxOnly(CharSequence val) {
			campo = val;
		}
	}

	static class TesteCharSequenceMinOnly {
		@Size(messageParameters = { "par=Teste" }, min = 2)
		private CharSequence campo;

		TesteCharSequenceMinOnly() {
		}

		TesteCharSequenceMinOnly(CharSequence val) {
			campo = val;
		}
	}

	static class TestePrimitiveArray {
		@Size(messageParameters = { "par=Teste" }, min = 2, max = 4)
		private int[] campo;

		TestePrimitiveArray() {
		}

		TestePrimitiveArray(int[] val) {
			campo = val;
		}
	}

	static class TestePrimitiveArrayMaxOnly {
		@Size(messageParameters = { "par=Teste" }, min = 2, max = 4)
		private int[] campo;

		TestePrimitiveArrayMaxOnly() {
		}

		TestePrimitiveArrayMaxOnly(int[] val) {
			campo = val;
		}
	}

	static class TestePrimitiveArrayMinOnly {
		@Size(messageParameters = { "par=Teste" }, min = 2)
		private int[] campo;

		TestePrimitiveArrayMinOnly() {
		}

		TestePrimitiveArrayMinOnly(int[] val) {
			campo = val;
		}
	}

	@Override
	protected Class<?> getClassOnTest() {
		return SizeValidatorImpl.class;
	}
}
