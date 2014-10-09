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
import com.github.ldeitos.validation.impl.interpolator.ExtendedValidationBaseTest;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;

@AdditionalClasses({TestMessageSource.class})
public class SizeTest extends ExtendedValidationBaseTest {
	
	private static final String MENSAGEM_ESPERADA = "Size Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;
	
	@Test
	public void testPrimitiveArrayNull(){
		TestePrimitiveArray var = new TestePrimitiveArray();
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testPrimitiveArrayValidBottomLimit(){
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] {1, 2});
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testPrimitiveArrayValidTopLimit(){
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] {1, 2, 3, 4});
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testPrimitiveArrayValidBottomInvalid(){
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] {});
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testPrimitiveArrayValidTopInvalid(){
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] {1, 2, 3, 4, 5});
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testCollectionNull(){
		TesteCollection var = new TesteCollection();
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testCollectionValidBottomLimit(){
		TesteCollection var = new TesteCollection(asList(true, false));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testCollectionValidTopLimit(){
		TesteCollection var = new TesteCollection(asList(true, false, true, false));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testCollectionValidBottomInvalid(){
		TesteCollection var = new TesteCollection(new ArrayList<String>());
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testCollectionValidTopInvalid(){
		TesteCollection var = new TesteCollection(asList(true, true, false, true, false));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMapNull(){
		TesteMap var = new TesteMap();
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMapValidBottomLimit(){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMapValidTopLimit(){
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
	public void testMapValidBottomInvalid(){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMapValidTopInvalid(){
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
	
	static class TestePrimitiveArray {
		@Size(messageParameters = {"par=Teste"}, min = 2, max = 4)
		private int[] campo;
		
		TestePrimitiveArray(){
		}
		
		TestePrimitiveArray(int[] val){
			campo = val;
		}
	}
	
	@Test
	public void testCharSequenceNull(){
		TesteCharSequence var = new TesteCharSequence();
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testCharSequenceValidBottomLimit(){
		TesteCharSequence var = new TesteCharSequence("12");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testCharSequenceValidTopLimit(){
		TesteCharSequence var = new TesteCharSequence("1234");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testCharSequenceValidBottomInvalid(){
		TesteCharSequence var = new TesteCharSequence("");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testCharSequenceValidTopInvalid(){
		TesteCharSequence var = new TesteCharSequence("12345");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test(expected = ValidationException.class)
	public void testInvalidType(){
		TesteInvalidType var = new TesteInvalidType();
		validador.validate(var);
	}
	
	static class TesteInvalidType {
		@Size(messageParameters = {"par=Teste"}, min = 2, max = 4)
		private Integer campo = 1;
	}
	
	static class TesteCollection {
		@Size(messageParameters = {"par=Teste"}, min = 2, max = 4)
		private Collection<?> campo;
		
		TesteCollection(){
		}
		
		TesteCollection(Collection<?> val){
			campo = val;
		}
	}
	
	static class TesteMap {
		@Size(messageParameters = {"par=Teste"}, min = 2, max = 4)
		private Map<?, ?> campo;
		
		TesteMap(){
		}
		
		TesteMap(Map<?, ?> val){
			campo = val;
		}
	}
	
	static class TesteCharSequence {
		@Size(messageParameters = {"par=Teste"}, min = 2, max = 4)
		private CharSequence campo;
		
		TesteCharSequence(){
		}
		
		TesteCharSequence(CharSequence val){
			campo = val;
		}
	}
}
