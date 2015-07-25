package com.github.ldeitos.tarcius;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.tarcius.audit.AuditContext;
import com.github.ldeitos.tarcius.audit.auditprocessor.AuditProcessorImpl;
import com.github.ldeitos.tarcius.audit.interceptor.AuditInterceptor;
import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.exception.AuditException;
import com.github.ldeitos.tarcius.producer.TarciusProducer;
import com.github.ldeitos.tarcius.support.TestAuditDataDispatcher;
import com.github.ldeitos.tarcius.support.TestAuditDataFormatter;
import com.github.ldeitos.tarcius.support.Teste;
import com.github.ldeitos.tarcius.support.ToAudit;
import com.github.ldeitos.util.ManualContext;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ToAudit.class, AuditContext.class, AuditInterceptor.class,
    TestAuditDataDispatcher.class, TestAuditDataFormatter.class, TarciusProducer.class,
	AuditProcessorImpl.class, ManualContext.class })
@InRequestScope
public class AuditInterruptOnErrorTest {

	@Inject
	private ToAudit test;

	@Produces
	@ProducesAlternative
	private ConfigInfoProvider provider = new ConfigInfoProvider() {
		@Override
		public String getConfigFileName() {
			return "META-INF/tarcius_interruptonerror.xml";
		};

		@Override
		protected boolean isInTest() {
			return true;
		};
	};

	@AfterClass
	public static void shutdown() {
		Configuration.reset();
	}

	@Test(expected = AuditException.class)
	public void testAuditoriaCustomResolver() {
		test.testInvalidCustomResolver(new Teste("valPar"));
	}

}
