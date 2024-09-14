package com.github.ldeitos.constraint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

import io.github.cdiunit.AdditionalClasses;
import org.junit.Test;

import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;
import com.github.ldeitos.validators.MinDecimalValidatorImpl;

@AdditionalClasses({ TestMessageSource.class, MinDecimalValidatorImpl.class })
public class DecimalMinTest extends BaseTest {

	private static final String MENSAGEM_ESPERADA = "DecimalMin Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;

	@Test
	public void testMinStringGreaterValid() {
		TesteString var = new TesteString("3.002");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinStringEquals() {
		TesteString var = new TesteString("3.001");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinStringInvalid() {
		TesteString var = new TesteString("3.0009");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test(expected = ValidationException.class)
	public void testMinStringEmptyInvalid() {
		TesteString var = new TesteString("");
		validador.validate(var);
	}

	@Test
	public void testMinStringExclusiveInvalid() {
		TesteStringExclusive var = new TesteStringExclusive("3.001");
		Set<ConstraintViolation<TesteStringExclusive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinIntegerNullValid() {
		TesteInteger var = new TesteInteger();
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinIntegerGreaterValid() {
		TesteInteger var = new TesteInteger(4);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinIntegerEqualValid() {
		TesteInteger var = new TesteInteger(3);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinIntegerInvalid() {
		TesteInteger var = new TesteInteger(2);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinLongNullValid() {
		TesteLong var = new TesteLong();
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinLongGreaterValid() {
		TesteLong var = new TesteLong(4l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinLongEqualValid() {
		TesteLong var = new TesteLong(3l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinLongInvalid() {
		TesteLong var = new TesteLong(2l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinByteNullValid() {
		TesteByte var = new TesteByte();
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinByteGreaterValid() {
		byte b = 4;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinByteEqualValid() {
		byte b = 3;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinByteInvalid() {
		byte b = 2;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinShortNullValid() {
		TesteShort var = new TesteShort();
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinShortGreaterValid() {
		short b = 4;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinShortEqualValid() {
		short b = 3;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinShortInvalid() {
		short b = 2;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinBigDecimalNullValid() {
		TesteBigDecimal var = new TesteBigDecimal();
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinBigDecimalGreaterValid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("3.002"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinBigDecimalEqualValid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("3.001"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinBigDecimalEqualExclusiveInvalid() {
		TesteBigDecimalExclusive var = new TesteBigDecimalExclusive(new BigDecimal("3.001"));
		Set<ConstraintViolation<TesteBigDecimalExclusive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinBigDecimalInvalid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("3.0009"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinBigIntegerNullValid() {
		TesteBigInteger var = new TesteBigInteger();
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinBigIntegerGreaterValid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("4"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinBigIntegerEqualValid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("3"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinBigIntegerInvalid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("2"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinIntGreaterValid() {
		TesteIntPrimitive var = new TesteIntPrimitive(4);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinIntEqualValid() {
		TesteIntPrimitive var = new TesteIntPrimitive(3);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinIntInvalid() {
		TesteIntPrimitive var = new TesteIntPrimitive(2);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinLongPrimitiveGreaterValid() {
		TesteLongPrimitive var = new TesteLongPrimitive(4);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinLongPrimitiveEqualValid() {
		TesteLongPrimitive var = new TesteLongPrimitive(3);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinLongPrimitiveInvalid() {
		TesteLongPrimitive var = new TesteLongPrimitive(2);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinShortPrimitiveGreaterValid() {
		short n = 4;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinShortPrimitiveEqualValid() {
		short n = 3;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinShortPrimitiveInvalid() {
		short n = 2;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMinBytePrimitiveGreaterValid() {
		byte n = 4;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinBytePrimitiveEqualValid() {
		byte n = 3;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMinBytePrimitiveInvalid() {
		byte n = 2;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
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
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3.001")
		private List<?> campo = new ArrayList();
	}

	private static class TesteString {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3.001")
		String campo;

		TesteString(String val) {
			campo = val;
		}
	}

	private static class TesteStringExclusive {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3.001", inclusive = false)
		String campo;

		TesteStringExclusive(String val) {
			campo = val;
		}
	}

	private static class TesteInteger {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		Integer campo;

		TesteInteger() {
		}

		TesteInteger(Integer val) {
			campo = val;
		}
	}

	private static class TesteLong {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		Long campo;

		TesteLong() {
		}

		TesteLong(Long val) {
			campo = val;
		}
	}

	private static class TesteByte {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		Byte campo;

		TesteByte() {
		}

		TesteByte(Byte val) {
			campo = val;
		}
	}

	private static class TesteShort {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		Short campo;

		TesteShort() {
		}

		TesteShort(Short val) {
			campo = val;
		}
	}

	private static class TesteBigInteger {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		BigInteger campo;

		TesteBigInteger() {
		}

		TesteBigInteger(BigInteger val) {
			campo = val;
		}
	}

	private static class TesteBigDecimal {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3.001")
		BigDecimal campo;

		TesteBigDecimal() {
		}

		TesteBigDecimal(BigDecimal val) {
			campo = val;
		}
	}

	private static class TesteBigDecimalExclusive {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3.001", inclusive = false)
		BigDecimal campo;

		TesteBigDecimalExclusive(BigDecimal val) {
			campo = val;
		}
	}

	private static class TesteIntPrimitive {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		int campo;

		TesteIntPrimitive(int val) {
			campo = val;
		}
	}

	private static class TesteLongPrimitive {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		long campo;

		TesteLongPrimitive(long val) {
			campo = val;
		}
	}

	private static class TesteBytePrimitive {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		byte campo;

		TesteBytePrimitive(byte val) {
			campo = val;
		}
	}

	private static class TesteShortPrimitive {
		@DecimalMin(messageParameters = { "par=Teste" }, value = "3")
		short campo;

		TesteShortPrimitive(short val) {
			campo = val;
		}
	}

	@Override
	protected Class<?> getClassOnTest() {
		return MinDecimalValidatorImpl.class;
	}
}
