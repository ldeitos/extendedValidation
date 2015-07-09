package com.github.ldeitos.tarcius;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Queue;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.tarcius.audit.AuditContext;
import com.github.ldeitos.tarcius.audit.AuditDataSource;
import com.github.ldeitos.tarcius.audit.auditprocessor.AuditProcessorImpl;
import com.github.ldeitos.tarcius.audit.interceptor.AuditInterceptor;
import com.github.ldeitos.tarcius.audit.resolver.DefaultStringResolver;
import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.producer.TarciusProducer;
import com.github.ldeitos.tarcius.support.TestAuditDataDispatcher;
import com.github.ldeitos.tarcius.support.TestAuditDataFormatter;
import com.github.ldeitos.tarcius.support.ToAudit;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ToAudit.class, DefaultStringResolver.class, AuditContext.class, AuditInterceptor.class,
	TestAuditDataDispatcher.class, TestAuditDataFormatter.class, ConfigInfoProvider.class,
    TarciusProducer.class, AuditProcessorImpl.class })
@InRequestScope
public class AuditContextTest {

	@Inject
	@Any
	private Instance<AuditContext> contextProvider;

	@Inject
	private ToAudit test;

	@AfterClass
	public static void shutdown() {
		Configuration.reset();
	}

	@Test
	public void testAuditoriaComParametroStringNumericoAuditado() {
		AuditContext ctx = contextProvider.get();
		String par1 = "chamada1";
		String par2 = "chamada2";
		Integer par3 = 10;

		assertNotNull(ctx);
		String id = ctx.getId();

		assertTrue(ctx.getAuditReferences().isEmpty());
		assertTrue(ctx.getAuditEntries().isEmpty());

		test.testStringParam(par1);
		test.testStringIntParam(par2, par3);

		ctx = null;
		ctx = contextProvider.get();

		assertNotNull(ctx);
		assertEquals(id, ctx.getId());

		Queue<String> auditReferences = new LinkedList<String>(ctx.getAuditReferences());

		assertEquals("parameterTest", auditReferences.poll());
		assertEquals("parameterTest2", auditReferences.poll());
		assertEquals(2, ctx.getAuditEntries().size());

		AuditDataSource ads = ctx.getAuditEntries().get("parameterTest");
		assertNotNull(ads);

		auditReferences = new LinkedList<String>(ads.getAuditParameterReferences());

		assertEquals("parName", auditReferences.poll());
		assertSame(par1, ads.getParameterValues().get("parName"));
		assertEquals(par1, ads.getResolvedParameterValues().get("parName"));

		ads = ctx.getAuditEntries().get("parameterTest2");
		assertNotNull(ads);

		auditReferences = new LinkedList<String>(ads.getAuditParameterReferences());

		assertEquals("par1", auditReferences.poll());
		assertSame(par2, ads.getParameterValues().get("par1"));
		assertEquals(par2, ads.getResolvedParameterValues().get("par1"));

		assertEquals("par2", auditReferences.poll());
		assertSame(par3, ads.getParameterValues().get("par2"));
		assertNotSame(par3, ads.getResolvedParameterValues().get("par2"));
		assertEquals(par3.toString(), ads.getResolvedParameterValues().get("par2"));

	}

}
