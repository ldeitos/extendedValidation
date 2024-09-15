package com.github.ldeitos.constraint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import io.github.cdiunit.AdditionalClasses;
import org.junit.Test;

import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;

@AdditionalClasses({TestMessageSource.class})
public class AssertTrueTest extends BaseTest {
	
	private static final String MENSAGEM_ESPERADA = "AssertTrue Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;
	
	@Test
	public void testTrueValue(){
		TestePrimitive var = new TestePrimitive(true);
		Set<ConstraintViolation<TestePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testFalseValue(){
		TestePrimitive var = new TestePrimitive();
		Set<ConstraintViolation<TestePrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testTrueObjectValue(){
		TesteObject var = new TesteObject(true);
		Set<ConstraintViolation<TesteObject>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testNullObjectValue(){
		TesteObject var = new TesteObject();
		Set<ConstraintViolation<TesteObject>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testFalseObjectValue(){
		TesteObject var = new TesteObject(false);
		Set<ConstraintViolation<TesteObject>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	static class TestePrimitive {
		@AssertTrue(messageParameters = {"par=Teste"})
		private boolean campo;
		
		TestePrimitive(){
		}
		
		TestePrimitive(boolean val){
			campo = val;
		}
	}
	
	static class TesteObject {
		@AssertTrue(messageParameters = {"par=Teste"})
		private Boolean campo;
		
		TesteObject(){
		}
		
		TesteObject(boolean val){
			campo = val;
		}
	}
}
