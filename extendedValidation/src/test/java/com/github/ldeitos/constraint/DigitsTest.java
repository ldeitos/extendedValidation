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

@AdditionalClasses({TestMessageSource.class})
public class DigitsTest extends ExtendedValidationBaseTest {
	
	private static final String MENSAGEM_ESPERADA = "Digits Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;
	
	
	@Test
	public void testDigitsStringLowerValid(){
		TesteString var = new TesteString("1");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsStringEquals(){
		TesteString var = new TesteString("99.99999");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsIntegerStringInvalid(){
		TesteString var = new TesteString("999.99999");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsFractionStringInvalid(){
		TesteString var = new TesteString("99.999999");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test(expected = ValidationException.class)
	public void testDigitsStringEmptyInvalid(){
		TesteString var = new TesteString("");
		validador.validate(var);
	}
	
	@Test
	public void testDigitsIntegerNullValid(){
		TesteInteger var = new TesteInteger();
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testDigitsIntegerLowerValid(){
		TesteInteger var = new TesteInteger(2);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsIntegerEqualValid(){
		TesteInteger var = new TesteInteger(88);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsIntegerGreaterInvalid(){
		TesteInteger var = new TesteInteger(111);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsLongNullValid(){
		TesteLong var = new TesteLong();
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testDigitsLongLowerValid(){
		TesteLong var = new TesteLong(0l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsLongEqualValid(){
		TesteLong var = new TesteLong(77l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsLongGreaterInvalid(){
		TesteLong var = new TesteLong(222l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsByteNullValid(){
		TesteByte var = new TesteByte();
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testDigitsByteLowerValid(){
		byte b = 2;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsByteEqualValid(){
		byte b = 66;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsByteGreaterInvalid(){
		byte b = 100;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsShortNullValid(){
		TesteShort var = new TesteShort();
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testDigitsShortLowerValid(){
		short b = 55;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsShortEqualValid(){
		short b = 90;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsShortInvalid(){
		short b = 909;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsBigDecimalNullValid(){
		TesteBigDecimal var = new TesteBigDecimal();
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testDigitsBigDecimalLowerValid(){
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("0.00001"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsBigDecimalEqualValid(){
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("33.00101"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsBigDecimalIntegerInvalid(){
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("100.00002"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsBigDecimalFractionInvalid(){
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("10.000002"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsBigIntegerNullValid(){
		TesteBigInteger var = new TesteBigInteger();
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testDigitsBigIntegerLowerValid(){
		TesteBigInteger var = new TesteBigInteger(new BigInteger("2"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsBigIntegerEqualValid(){
		TesteBigInteger var = new TesteBigInteger(new BigInteger("53"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsBigIntegerGreaterInvalid(){
		TesteBigInteger var = new TesteBigInteger(new BigInteger("400"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsIntLowerValid(){
		TesteIntPrimitive var = new TesteIntPrimitive(2);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsIntEqualValid(){
		TesteIntPrimitive var = new TesteIntPrimitive(20);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsIntGreaterInvalid(){
		TesteIntPrimitive var = new TesteIntPrimitive(478);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsLongPrimitiveLowerValid(){
		TesteLongPrimitive var = new TesteLongPrimitive(2);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsLongPrimitiveEqualValid(){
		TesteLongPrimitive var = new TesteLongPrimitive(10);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsLongPrimitiveGreaterInvalid(){
		TesteLongPrimitive var = new TesteLongPrimitive(102);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsShortPrimitiveLowerValid(){
		short n = 2;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsShortPrimitiveEqualValid(){
		short n = 60;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsShortPrimitiveInvalid(){
		short n = 100;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testDigitsBytePrimitiveLowerValid(){
		byte n = 2;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsBytePrimitiveEqualValid(){
		byte n = 55;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDigitsBytePrimitiveInvalid(){
		byte n = 100;
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
	
	@Test(expected = ValidationException.class)
	public void testInvalidIntegerParam(){
		TesteInvalidInteger var = new TesteInvalidInteger();
		validador.validate(var);
	}
	
	@Test(expected = ValidationException.class)
	public void testInvalidFractionParam(){
		TesteInvalidFraction var = new TesteInvalidFraction();
		validador.validate(var);
	}
	
	static class TesteInvalidInteger {
		@Digits(messageParameters = {"par=Teste"}, integer = -1, fraction = 5)
		private int i = 1;
	}
	
	static class TesteInvalidFraction {
		@Digits(messageParameters = {"par=Teste"}, integer = 1, fraction = -1)
		private int i = 1;
	}
	
	static class TesteInvalidType {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		private List<?> campo = new ArrayList();
	}
	
	private static class TesteString {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		String campo;
		
		TesteString(String val){
			campo = val;
		}
	}
	
	private static class TesteInteger {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 0)
		Integer campo;
		
		TesteInteger(){
		}
		
		TesteInteger(Integer val){
			campo = val;
		}
	}
	
	private static class TesteLong {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 0)
		Long campo;
		
		TesteLong(){
		}
		
		TesteLong(Long val){
			campo = val;
		}
	}
	
	private static class TesteByte {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 0)
		Byte campo;
		
		TesteByte(){
		}
		
		TesteByte(Byte val){
			campo = val;
		}
	}
	
	private static class TesteShort {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		Short campo;
		
		TesteShort(){
		}
		
		TesteShort(Short val){
			campo = val;
		}
	}
	
	private static class TesteBigInteger {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		BigInteger campo;
		
		TesteBigInteger(){
		}
		
		TesteBigInteger(BigInteger val){
			campo = val;
		}
	}
	
	private static class TesteBigDecimal {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		BigDecimal campo;
		
		TesteBigDecimal(){
		}
		
		TesteBigDecimal(BigDecimal val){
			campo = val;
		}
	}
	
	private static class TesteIntPrimitive {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		int campo;
		
		TesteIntPrimitive(int val){
			campo = val;
		}
	}
	
	private static class TesteLongPrimitive {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		long campo;
		
		TesteLongPrimitive(long val){
			campo = val;
		}
	}
	
	private static class TesteBytePrimitive {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		byte campo;
		
		TesteBytePrimitive(byte val){
			campo = val;
		}
	}
	
	private static class TesteShortPrimitive {
		@Digits(messageParameters = {"par=Teste"}, integer = 2, fraction = 5)
		short campo;
		
		TesteShortPrimitive(short val){
			campo = val;
		}
	}
}
