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
import com.github.ldeitos.validators.MaxDecimalValidatorImpl;

@AdditionalClasses({ TestMessageSource.class, MaxDecimalValidatorImpl.class })
public class DecimalMaxTest extends BaseTest {

	private static final String MENSAGEM_ESPERADA = "DecimalMax Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;

	@Test
	public void testMaxStringLowerValid() {
		TesteString var = new TesteString("3.0009");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxStringEquals() {
		TesteString var = new TesteString("3.001");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxStringInvalid() {
		TesteString var = new TesteString("3.002");
		Set<ConstraintViolation<TesteString>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test(expected = ValidationException.class)
	public void testMaxStringEmptyInvalid() {
		TesteString var = new TesteString("");
		validador.validate(var);
	}

	@Test
	public void testMaxStringExclusiveInvalid() {
		TesteStringExclusive var = new TesteStringExclusive("3.001");
		Set<ConstraintViolation<TesteStringExclusive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxIntegerNullValid() {
		TesteInteger var = new TesteInteger();
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxIntegerLowerValid() {
		TesteInteger var = new TesteInteger(2);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxIntegerEqualValid() {
		TesteInteger var = new TesteInteger(3);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxIntegerInvalid() {
		TesteInteger var = new TesteInteger(4);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxLongNullValid() {
		TesteLong var = new TesteLong();
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxLongLowerValid() {
		TesteLong var = new TesteLong(2l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxLongEqualValid() {
		TesteLong var = new TesteLong(3l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxLongInvalid() {
		TesteLong var = new TesteLong(4l);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxByteNullValid() {
		TesteByte var = new TesteByte();
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxByteLowerValid() {
		byte b = 2;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxByteEqualValid() {
		byte b = 3;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxByteInvalid() {
		byte b = 4;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxShortNullValid() {
		TesteShort var = new TesteShort();
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxShortLowerValid() {
		short b = 2;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxShortEqualValid() {
		short b = 3;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxShortInvalid() {
		short b = 4;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxBigDecimalNullValid() {
		TesteBigDecimal var = new TesteBigDecimal();
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBigDecimalLowerValid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("3.0009"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBigDecimalEqualValid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("3.001"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBigDecimalEqualExclusiveInvalid() {
		TesteBigDecimalExclusive var = new TesteBigDecimalExclusive(new BigDecimal("3.001"));
		Set<ConstraintViolation<TesteBigDecimalExclusive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxBigDecimalInvalid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal("3.002"));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxBigIntegerNullValid() {
		TesteBigInteger var = new TesteBigInteger();
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBigIntegerLowerValid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("2"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBigIntegerEqualValid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("3"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBigIntegerInvalid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("4"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxIntLowerValid() {
		TesteIntPrimitive var = new TesteIntPrimitive(2);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxIntEqualValid() {
		TesteIntPrimitive var = new TesteIntPrimitive(3);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxIntInvalid() {
		TesteIntPrimitive var = new TesteIntPrimitive(4);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxLongPrimitiveLowerValid() {
		TesteLongPrimitive var = new TesteLongPrimitive(2);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxLongPrimitiveEqualValid() {
		TesteLongPrimitive var = new TesteLongPrimitive(3);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxLongPrimitiveInvalid() {
		TesteLongPrimitive var = new TesteLongPrimitive(4);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxShortPrimitiveLowerValid() {
		short n = 2;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxShortPrimitiveEqualValid() {
		short n = 3;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxShortPrimitiveInvalid() {
		short n = 4;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMaxBytePrimitiveLowerValid() {
		byte n = 2;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBytePrimitiveEqualValid() {
		byte n = 3;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMaxBytePrimitiveInvalid() {
		byte n = 4;
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
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3.001")
		private List<?> campo = new ArrayList();
	}

	private static class TesteString {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3.001")
		String campo;

		TesteString(String val) {
			campo = val;
		}
	}

	private static class TesteStringExclusive {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3.001", inclusive = false)
		String campo;

		TesteStringExclusive(String val) {
			campo = val;
		}
	}

	private static class TesteInteger {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		Integer campo;

		TesteInteger() {
		}

		TesteInteger(Integer val) {
			campo = val;
		}
	}

	private static class TesteLong {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		Long campo;

		TesteLong() {
		}

		TesteLong(Long val) {
			campo = val;
		}
	}

	private static class TesteByte {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		Byte campo;

		TesteByte() {
		}

		TesteByte(Byte val) {
			campo = val;
		}
	}

	private static class TesteShort {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		Short campo;

		TesteShort() {
		}

		TesteShort(Short val) {
			campo = val;
		}
	}

	private static class TesteBigInteger {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		BigInteger campo;

		TesteBigInteger() {
		}

		TesteBigInteger(BigInteger val) {
			campo = val;
		}
	}

	private static class TesteBigDecimal {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3.001")
		BigDecimal campo;

		TesteBigDecimal() {
		}

		TesteBigDecimal(BigDecimal val) {
			campo = val;
		}
	}

	private static class TesteBigDecimalExclusive {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3.001", inclusive = false)
		BigDecimal campo;

		TesteBigDecimalExclusive(BigDecimal val) {
			campo = val;
		}
	}

	private static class TesteIntPrimitive {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		int campo;

		TesteIntPrimitive(int val) {
			campo = val;
		}
	}

	private static class TesteLongPrimitive {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		long campo;

		TesteLongPrimitive(long val) {
			campo = val;
		}
	}

	private static class TesteBytePrimitive {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		byte campo;

		TesteBytePrimitive(byte val) {
			campo = val;
		}
	}

	private static class TesteShortPrimitive {
		@DecimalMax(messageParameters = { "par=Teste" }, value = "3")
		short campo;

		TesteShortPrimitive(short val) {
			campo = val;
		}
	}

	@Override
	protected Class<?> getClassOnTest() {
		return MaxDecimalValidatorImpl.class;
	}
}
