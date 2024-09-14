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
import com.github.ldeitos.validators.RangeValidatorImpl;

@AdditionalClasses({ TestMessageSource.class, RangeValidatorImpl.class })
public class RangeTest extends BaseTest {

	private static final String MENSAGEM_ESPERADA = "Range Teste";

	@Inject
	@ExtendedValidator
	private Validator validador;

	@Test
	public void testRangeIntegerNullValid() {
		TesteInteger var = new TesteInteger();
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeIntegerMinEqualValid() {
		TesteInteger var = new TesteInteger(3);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeIntegerMaxEqualValid() {
		TesteInteger var = new TesteInteger(5);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeIntegerBetweenValid() {
		TesteInteger var = new TesteInteger(4);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeIntegerMinInvalid() {
		TesteInteger var = new TesteInteger(2);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeIntegerMaxInvalid() {
		TesteInteger var = new TesteInteger(6);
		Set<ConstraintViolation<TesteInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeLongNullValid() {
		TesteLong var = new TesteLong();
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeLongMinEqualValid() {
		TesteLong var = new TesteLong(3L);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeLongMaxEqualValid() {
		TesteLong var = new TesteLong(5L);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeLongBetweenValid() {
		TesteLong var = new TesteLong(4L);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeLongMinInvalid() {
		TesteLong var = new TesteLong(2L);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeLongMaxInvalid() {
		TesteLong var = new TesteLong(6L);
		Set<ConstraintViolation<TesteLong>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeByteNullValid() {
		TesteByte var = new TesteByte();
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeByteMinEqualValid() {
		byte b = 3;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeByteMaxEqualValid() {
		byte b = 5;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeByteBetweenValid() {
		byte b = 4;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeByteMinInvalid() {
		byte b = 2;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeByteMaxInvalid() {
		byte b = 6;
		TesteByte var = new TesteByte(b);
		Set<ConstraintViolation<TesteByte>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeShortNullValid() {
		TesteShort var = new TesteShort();
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeShortMinEqualValid() {
		short b = 3;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeShortMaxEqualValid() {
		short b = 5;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeShortBetweenValid() {
		short b = 4;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeShortMinInvalid() {
		short b = 2;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeShortMaxInvalid() {
		short b = 6;
		TesteShort var = new TesteShort(b);
		Set<ConstraintViolation<TesteShort>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeBigDecimalNullValid() {
		TesteBigDecimal var = new TesteBigDecimal();
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBigDecimalMinEqualValid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal(3L));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBigDecimalMaxEqualValid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal(5L));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBigDecimalBetweenValid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal(4L));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBigDecimalMinInvalid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal(2L));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeBigDecimalMaxInvalid() {
		TesteBigDecimal var = new TesteBigDecimal(new BigDecimal(6L));
		Set<ConstraintViolation<TesteBigDecimal>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeBigIntegerNullValid() {
		TesteBigInteger var = new TesteBigInteger();
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBigIntegerMinEqualValid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("3"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBigIntegerMaxEqualValid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("5"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBigIntegerBetweenValid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("4"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBigIntegerMinInvalid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("2"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeBigIntegerMaxInvalid() {
		TesteBigInteger var = new TesteBigInteger(new BigInteger("6"));
		Set<ConstraintViolation<TesteBigInteger>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeIntPrimitiveMinEqualValid() {
		TesteIntPrimitive var = new TesteIntPrimitive(3);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeIntPrimitiveMaxEqualValid() {
		TesteIntPrimitive var = new TesteIntPrimitive(5);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeIntPrimitiveBetweenValid() {
		TesteIntPrimitive var = new TesteIntPrimitive(4);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeIntPrimitiveMinInvalid() {
		TesteIntPrimitive var = new TesteIntPrimitive(2);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeIntPrimitiveMaxInvalid() {
		TesteIntPrimitive var = new TesteIntPrimitive(6);
		Set<ConstraintViolation<TesteIntPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeLongPrimitiveMinEqualValid() {
		TesteLongPrimitive var = new TesteLongPrimitive(3);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeLongPrimitiveMaxEqualValid() {
		TesteLongPrimitive var = new TesteLongPrimitive(5);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeLongPrimitiveBetweenValid() {
		TesteLongPrimitive var = new TesteLongPrimitive(4);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeLongPrimitiveMinInvalid() {
		TesteLongPrimitive var = new TesteLongPrimitive(2);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeLongPrimitiveMaxInvalid() {
		TesteLongPrimitive var = new TesteLongPrimitive(6);
		Set<ConstraintViolation<TesteLongPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeShortPrimitiveMinEqualValid() {
		short n = 3;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeShortPrimitiveMaxnEqualValid() {
		short n = 5;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeShortPrimitiveBetweenValid() {
		short n = 4;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeShortPrimitiveMinInvalid() {
		short n = 2;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeShortPrimitiveMaxInvalid() {
		short n = 6;
		TesteShortPrimitive var = new TesteShortPrimitive(n);
		Set<ConstraintViolation<TesteShortPrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeBytePrimitiveMinEqualValid() {
		byte n = 3;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	public void testRangeBytePrimitiveMaxEqualValid() {
		byte n = 5;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	public void testRangeBytePrimitiveBetweenValid() {
		byte n = 4;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testRangeBytePrimitiveMinInvalid() {
		byte n = 2;
		TesteBytePrimitive var = new TesteBytePrimitive(n);
		Set<ConstraintViolation<TesteBytePrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testRangeBytePrimitiveMaxInvalid() {
		byte n = 6;
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
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		private List<?> campo = new ArrayList();
	}

	private static class TesteInteger {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		Integer campo;

		TesteInteger() {
		}

		TesteInteger(Integer val) {
			campo = val;
		}
	}

	private static class TesteLong {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		Long campo;

		TesteLong() {
		}

		TesteLong(Long val) {
			campo = val;
		}
	}

	private static class TesteByte {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		Byte campo;

		TesteByte() {
		}

		TesteByte(Byte val) {
			campo = val;
		}
	}

	private static class TesteShort {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		Short campo;

		TesteShort() {
		}

		TesteShort(Short val) {
			campo = val;
		}
	}

	private static class TesteBigInteger {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		BigInteger campo;

		TesteBigInteger() {
		}

		TesteBigInteger(BigInteger val) {
			campo = val;
		}
	}

	private static class TesteBigDecimal {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		BigDecimal campo;

		TesteBigDecimal() {
		}

		TesteBigDecimal(BigDecimal val) {
			campo = val;
		}
	}

	private static class TesteIntPrimitive {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		int campo;

		TesteIntPrimitive(int val) {
			campo = val;
		}
	}

	private static class TesteLongPrimitive {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		long campo;

		TesteLongPrimitive(long val) {
			campo = val;
		}
	}

	private static class TesteBytePrimitive {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		byte campo;

		TesteBytePrimitive(byte val) {
			campo = val;
		}
	}

	private static class TesteShortPrimitive {
		@Range(messageParameters = { "par=Teste" }, min = 3L, max = 5L)
		short campo;

		TesteShortPrimitive(short val) {
			campo = val;
		}
	}

	@Override
	protected Class<?> getClassOnTest() {
		return RangeValidatorImpl.class;
	}
}
