package com.github.ldeitos.restutil;

import static com.github.ldeitos.restutil.ParametersParser.parse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParametersParserTest {

	@Test
	public void testParseParams() {
		String strParams = "kind,items(title,characteristics(length))";
		ParamDescriptor root = parse(strParams);
		ParamDescriptor paramDescriptor;

		assertTrue(root.isRoot());
		assertTrue(root.isComplex());
		assertArrayEquals(new String[] { "kind", "items" }, root.getInnerParamsNames());

		paramDescriptor = root.getInnerParam("kind");

		assertTrue(paramDescriptor.isSimple());

		paramDescriptor = root.getInnerParam("items");

		assertTrue(paramDescriptor.isComplex());
		assertArrayEquals(new String[] { "title", "characteristics" }, paramDescriptor.getInnerParamsNames());

		paramDescriptor = root.getInnerParam("items").getInnerParam("title");

		assertTrue(paramDescriptor.isSimple());

		paramDescriptor = root.getInnerParam("items").getInnerParam("characteristics");

		assertTrue(paramDescriptor.isComplex());
		assertArrayEquals(new String[] { "length" }, paramDescriptor.getInnerParamsNames());
	}

	@Test
	public void testParseParamsManyComplexTypes() {
		String strParams = "kind,items(title,characteristics(length)),other,complex(field,otherField),oneMore";
		ParamDescriptor root = parse(strParams);
		ParamDescriptor paramDescriptor;

		assertTrue(root.isRoot());
		assertTrue(root.isComplex());
		assertArrayEquals(new String[] { "kind", "items", "other", "complex", "oneMore" },
		    root.getInnerParamsNames());

		paramDescriptor = root.getInnerParam("kind");

		assertTrue(paramDescriptor.isSimple());

		paramDescriptor = root.getInnerParam("items");

		assertTrue(paramDescriptor.isComplex());
		assertArrayEquals(new String[] { "title", "characteristics" }, paramDescriptor.getInnerParamsNames());

		paramDescriptor = root.getInnerParam("items").getInnerParam("title");

		assertTrue(paramDescriptor.isSimple());

		paramDescriptor = root.getInnerParam("items").getInnerParam("characteristics");

		assertTrue(paramDescriptor.isComplex());
		assertArrayEquals(new String[] { "length" }, paramDescriptor.getInnerParamsNames());

		paramDescriptor = root.getInnerParam("complex");

		assertTrue(paramDescriptor.isComplex());
		assertArrayEquals(new String[] { "field", "otherField" }, paramDescriptor.getInnerParamsNames());

		paramDescriptor = root.getInnerParam("complex").getInnerParam("field");

		assertTrue(paramDescriptor.isSimple());

		paramDescriptor = root.getInnerParam("complex").getInnerParam("otherField");

		assertTrue(paramDescriptor.isSimple());
	}

	@Test
	public void testAsString() {
		String strParams = "kind,items(title,characteristics(length)),other,complex(field,otherField),oneMore";
		ParamDescriptor root = parse(strParams);
		ParamDescriptor paramDescriptor;

		assertTrue(root.isRoot());
		assertTrue(root.isComplex());
		assertEquals(strParams, root.asString());

		paramDescriptor = root.getInnerParam("kind");

		assertTrue(paramDescriptor.isSimple());
		assertEquals("kind", paramDescriptor.asString());

		paramDescriptor = root.getInnerParam("items");

		assertTrue(paramDescriptor.isComplex());
		assertEquals("items(title,characteristics(length))", paramDescriptor.asString());

		paramDescriptor = root.getInnerParam("items").getInnerParam("title");
		assertTrue(paramDescriptor.isSimple());
		assertEquals("title", paramDescriptor.asString());

		paramDescriptor = root.getInnerParam("items").getInnerParam("characteristics");

		assertTrue(paramDescriptor.isComplex());
		assertEquals("characteristics(length)", paramDescriptor.asString());

		paramDescriptor = root.getInnerParam("other");

		assertTrue(paramDescriptor.isSimple());
		assertEquals("other", paramDescriptor.asString());

		paramDescriptor = root.getInnerParam("complex");

		assertTrue(paramDescriptor.isComplex());
		assertEquals("complex(field,otherField)", paramDescriptor.asString());

		paramDescriptor = root.getInnerParam("complex").getInnerParam("field");

		assertTrue(paramDescriptor.isSimple());
		assertEquals("field", paramDescriptor.asString());

		paramDescriptor = root.getInnerParam("complex").getInnerParam("otherField");

		assertTrue(paramDescriptor.isSimple());
		assertEquals("otherField", paramDescriptor.asString());

		paramDescriptor = root.getInnerParam("oneMore");

		assertTrue(paramDescriptor.isSimple());
		assertEquals("oneMore", paramDescriptor.asString());

	}

}
