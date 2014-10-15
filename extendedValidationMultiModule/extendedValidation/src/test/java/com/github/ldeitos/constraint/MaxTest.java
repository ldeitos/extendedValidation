package com.github.ldeitos.constraint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
import com.github.ldeitos.validators.MaxValidatorImpl;

@AdditionalClasses({TestMessageSource.class, MaxValidatorImpl.class })
public class MaxTest extends ExtendedValidationBaseTest {
	
	private static final String MENSAGEM_ESPERADA = "Max Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;
	
	@Test
	public void testMaxIntegerNullValid(){
		TesteInteger var = new TesteInteger();
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxIntegerLowerValid(){
		TesteInteger var = new TesteInteger(2);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxIntegerEqualValid(){
		TesteInteger var = new TesteInteger(3);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxIntegerInvalid(){
		TesteInteger var = new TesteInteger(4);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxLongNullValid(){
		TesteLong var = new TesteLong();
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxLongLowerValid(){
		TesteLong var = new TesteLong(2l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxLongEqualValid(){
		TesteLong var = new TesteLong(3l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxLongInvalid(){
		TesteLong var = new TesteLong(4l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxByteNullValid(){
		TesteByte var = new TesteByte();
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxByteLowerValid(){
		byte b = 2;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxByteEqualValid(){
		byte b = 3;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxByteInvalid(){
		byte b = 4;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxShortNullValid(){
		TesteShort var = new TesteShort();
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxShortLowerValid(){
		short b = 2;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxShortEqualValid(){
		short b = 3;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxShortInvalid(){
		short b = 4;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxBigDecimalNullValid(){
		TesteBigDecimal var = new TesteBigDecimal();
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBigDecimalLowerValid(){
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal(2L));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxBigDecimalEqualValid(){
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal(3L));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxBigDecimalInvalid(){
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal(4L));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxBigIntegerNullValid(){
		TesteBigInteger var = new TesteBigInteger();
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBigIntegerLowerValid(){
		TesteBigInteger var = new TesteBigInteger(new BigInteger("2"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxBigIntegerEqualValid(){
		TesteBigInteger var = new TesteBigInteger(new BigInteger("3"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxBigIntegerInvalid(){
		TesteBigInteger var = new TesteBigInteger(new BigInteger("4"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxIntLowerValid(){
		TesteIntPrimitive var = new TesteIntPrimitive(2);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxIntEqualValid(){
		TesteIntPrimitive var = new TesteIntPrimitive(3);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxIntInvalid(){
		TesteIntPrimitive var = new TesteIntPrimitive(4);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxLongPrimitiveLowerValid(){
		TesteLongPrimitive var = new TesteLongPrimitive(2);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxLongPrimitiveEqualValid(){
		TesteLongPrimitive var = new TesteLongPrimitive(3);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxLongPrimitiveInvalid(){
		TesteLongPrimitive var = new TesteLongPrimitive(4);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxShortPrimitiveLowerValid(){
		short n = 2;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxShortPrimitiveEqualValid(){
		short n = 3;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxShortPrimitiveInvalid(){
		short n = 4;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testMaxBytePrimitiveLowerValid(){
		byte n = 2;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxBytePrimitiveEqualValid(){
		byte n = 3;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testMaxBytePrimitiveInvalid(){
		byte n = 4;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
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
		@Max(messageParameters = {"par=Teste"}, value=3L)
		private List<?> campo = new ArrayList();
	}
	
	private static class TesteInteger {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		Integer campo;
		
		TesteInteger(){
		}
		
		TesteInteger(Integer val){
			campo = val;
		}
	}
	
	private static class TesteLong {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		Long campo;
		
		TesteLong(){
		}
		
		TesteLong(Long val){
			campo = val;
		}
	}
	
	private static class TesteByte {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		Byte campo;
		
		TesteByte(){
		}
		
		TesteByte(Byte val){
			campo = val;
		}
	}
	
	private static class TesteShort {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		Short campo;
		
		TesteShort(){
		}
		
		TesteShort(Short val){
			campo = val;
		}
	}
	
	private static class TesteBigInteger {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		BigInteger campo;
		
		TesteBigInteger(){
		}
		
		TesteBigInteger(BigInteger val){
			campo = val;
		}
	}
	
	private static class TesteBigDecimal {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		BigDecimal campo;
		
		TesteBigDecimal(){
		}
		
		TesteBigDecimal(BigDecimal val){
			campo = val;
		}
	}
	
	private static class TesteIntPrimitive {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		int campo;
		
		TesteIntPrimitive(int val){
			campo = val;
		}
	}
	
	private static class TesteLongPrimitive {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		long campo;
		
		TesteLongPrimitive(long val){
			campo = val;
		}
	}
	
	private static class TesteBytePrimitive {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		byte campo;
		
		TesteBytePrimitive(byte val){
			campo = val;
		}
	}
	
	private static class TesteShortPrimitive {
		@Max(messageParameters = {"par=Teste"}, value=3L)
		short campo;
		
		TesteShortPrimitive(short val){
			campo = val;
		}
	}
}
