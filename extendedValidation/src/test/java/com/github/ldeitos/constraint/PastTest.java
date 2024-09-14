package com.github.ldeitos.constraint;

import static java.util.Calendar.DAY_OF_YEAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
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
public class PastTest extends BaseTest {
	
	private static final String MENSAGEM_ESPERADA = "Past Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;
	
	@Test
	public void testDatePast(){
		Calendar cal = Calendar.getInstance();
		cal.add(DAY_OF_YEAR, -1);
		TesteDate var = new TesteDate(cal.getTime());
		Set<ConstraintViolation<TesteDate>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDateFuture(){
		Calendar cal = Calendar.getInstance();
		cal.add(DAY_OF_YEAR, 1);
		TesteDate var = new TesteDate(cal.getTime());
		Set<ConstraintViolation<TesteDate>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testCalendarPast(){
		Calendar cal = Calendar.getInstance();
		cal.add(DAY_OF_YEAR, -1);
		TesteCalendar var = new TesteCalendar(cal);
		Set<ConstraintViolation<TesteCalendar>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testCalendarFuture(){
		Calendar cal = Calendar.getInstance();
		cal.add(DAY_OF_YEAR, 1);
		TesteCalendar var = new TesteCalendar(cal);
		Set<ConstraintViolation<TesteCalendar>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	static class TesteDate {
		@Past(messageParameters = {"par=Teste"})
		private Date data;
		
		TesteDate(Date val){
			data = val;
		}
	}
	
	static class TesteCalendar {
		@Past(messageParameters = {"par=Teste"})
		private Calendar data;
		
		TesteCalendar(Calendar val){
			data = val;
		}
	}
}
