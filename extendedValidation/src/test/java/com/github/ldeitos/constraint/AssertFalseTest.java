package com.github.ldeitos.constraint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.jglue.cdiunit.AdditionalClasses;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.validation.impl.interpolator.ExtendedValidationBaseTest;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;

@AdditionalClasses({TestMessageSource.class})
public class AssertFalseTest extends ExtendedValidationBaseTest {
	
	private static final String MENSAGEM_ESPERADA = "AssertFalse Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;
	
	@Test
	public void testFalseValue(){
		TestePrimitive var = new TestePrimitive();
		Set<ConstraintViolation<TestePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testTrueValue(){
		TestePrimitive var = new TestePrimitive(true);
		Set<ConstraintViolation<TestePrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testFalseObjectValue(){
		TesteObject var = new TesteObject(false);
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
	public void testTrueObjectValue(){
		TesteObject var = new TesteObject(true);
		Set<ConstraintViolation<TesteObject>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	static class TestePrimitive {
		@AssertFalse(messageParameters = {"par=Teste"})
		private boolean campo;
		
		TestePrimitive(){
		}
		
		TestePrimitive(boolean val){
			campo = val;
		}
	}
	
	static class TesteObject {
		@AssertFalse(messageParameters = {"par=Teste"})
		private Boolean campo;
		
		TesteObject(){
		}
		
		TesteObject(boolean val){
			campo = val;
		}
	}
}
