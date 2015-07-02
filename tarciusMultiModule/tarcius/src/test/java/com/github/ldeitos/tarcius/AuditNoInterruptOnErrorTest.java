package com.github.ldeitos.tarcius;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.tarcius.audit.AuditContext;
import com.github.ldeitos.tarcius.audit.interceptor.AuditInterceptor;
import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.producer.TarciusProducer;
import com.github.ldeitos.tarcius.support.TestAuditDataDispatcher;
import com.github.ldeitos.tarcius.support.TestAuditDataFormatter;
import com.github.ldeitos.tarcius.support.Teste;
import com.github.ldeitos.tarcius.support.ToAudit;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ToAudit.class, AuditContext.class, AuditInterceptor.class,
    TestAuditDataDispatcher.class, TestAuditDataFormatter.class, TarciusProducer.class })
@InRequestScope
public class AuditNoInterruptOnErrorTest {

	@Inject
	private ToAudit test;

	@Produces
	@ProducesAlternative
	private ConfigInfoProvider provider = new ConfigInfoProvider() {
		@Override
		protected boolean isInTest() {
			return true;
		};
	};

	@Test
	public void testAuditoriaCustomResolver() {
		test.testInvalidCustomResolver(new Teste("valPar"));
	}

}
