package com.github.ldeitos.util;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.util.factory.AImpl;
import com.github.ldeitos.util.factory.BImpl;
import com.github.ldeitos.util.factory.IntegerFactory;
import com.github.ldeitos.util.factory.IntegerInterface;
import com.github.ldeitos.util.factory.OneImpl;
import com.github.ldeitos.util.factory.QIntegerTest;
import com.github.ldeitos.util.factory.QStringTest;
import com.github.ldeitos.util.factory.RuntimeQualifiedBeanFactory;
import com.github.ldeitos.util.factory.StringFactory;
import com.github.ldeitos.util.factory.StringInterface;
import com.github.ldeitos.util.factory.TwoImpl;

@RunWith(CdiRunner.class)
@AdditionalClasses({ AImpl.class, BImpl.class, OneImpl.class, TwoImpl.class, StringFactory.class,
    IntegerFactory.class })
public class RuntimeQualifiedFactoryTest {

	@Inject
	private RuntimeQualifiedBeanFactory<StringInterface, QStringTest, String> factoryString;

	@Inject
	private RuntimeQualifiedBeanFactory<IntegerInterface, QIntegerTest, Integer> factoryInteger;

	@Test
	public void testAStringImplProduces() {
		String qualifier = "A";
		StringInterface bean = factoryString.createBean(qualifier);
		Assert.assertEquals(qualifier, bean.get());
	}

	@Test
	public void testBStringImplProduces() {
		String qualifier = "B";
		StringInterface bean = factoryString.createBean(qualifier);
		Assert.assertEquals(qualifier, bean.get());
	}

	@Test
	public void test1IntegerImplProduces() {
		Integer qualifier = 1;
		IntegerInterface bean = factoryInteger.createBean(qualifier);
		Assert.assertEquals(qualifier, bean.get());
	}

	@Test
	public void test2IntegerImplProduces() {
		Integer qualifier = 2;
		IntegerInterface bean = factoryInteger.createBean(qualifier);
		Assert.assertEquals(qualifier, bean.get());
	}

}
