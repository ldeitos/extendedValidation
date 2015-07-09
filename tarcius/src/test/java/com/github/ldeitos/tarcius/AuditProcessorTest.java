package com.github.ldeitos.tarcius;

import static org.junit.Assert.assertEquals;

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
import com.github.ldeitos.tarcius.audit.AuditProcessor;
import com.github.ldeitos.tarcius.audit.auditprocessor.AuditProcessorImpl;
import com.github.ldeitos.tarcius.audit.resolver.DefaultStringResolver;
import com.github.ldeitos.tarcius.bootstrap.TarciusBootstrap;
import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.producer.TarciusProducer;
import com.github.ldeitos.tarcius.support.MessageDestination;
import com.github.ldeitos.tarcius.support.OutroTeste;
import com.github.ldeitos.tarcius.support.TestAuditDataDispatcher;
import com.github.ldeitos.tarcius.support.TestAuditDataFormatter;

@RunWith(CdiRunner.class)
@AdditionalClasses({ DefaultStringResolver.class, AuditContext.class, TestAuditDataDispatcher.class,
	TestAuditDataFormatter.class, TarciusProducer.class, TarciusBootstrap.class, AuditProcessorImpl.class })
@InRequestScope
public class AuditProcessorTest {

	private static final String QUEBRA = System.getProperty("line.separator");

	@Inject
	private AuditProcessor auditProcessor;

	@Produces
	@ProducesAlternative
	private ConfigInfoProvider configInfoProvier = new ConfigInfoProvider();

	@AfterClass
	public static void shutdown() {
		Configuration.reset();
	}

	@Test
	public void testAuditoriaSemParametroReferenciaNomeMetodo() {
		auditProcessor.doAudit("testRefMethodName");
		assertAudit("Método auditado: testRefMethodName");
	}

	@Test
	public void testAuditoriaParametroAnotadoEntidade() {
		auditProcessor.doAudit("parameterTest", new OutroTeste());
		assertAudit("Método auditado: parameterTest" + QUEBRA + "Parâmetro auditado: entity" + QUEBRA
		    + "Valor: teste anotação entidade.");
	}

	@Test
	public void testAuditoriaParametroAnotadoEntidadeDoisParametros() {
		auditProcessor.doAudit("parameterTest", new OutroTeste(), new OutroTeste());
		assertAudit("Método auditado: parameterTest" + QUEBRA + "Parâmetro auditado: entity" + QUEBRA
		    + "Valor: teste anotação entidade." + QUEBRA + "Parâmetro auditado: entity1" + QUEBRA
		    + "Valor: teste anotação entidade.");
	}

	@Test
	public void testAuditoriaParametroAnotadoEntidadeTresParametros() {
		auditProcessor.doAudit("parameterTest", new OutroTeste(), new OutroTeste(), new OutroTeste());
		assertAudit("Método auditado: parameterTest" + QUEBRA + "Parâmetro auditado: entity" + QUEBRA
		    + "Valor: teste anotação entidade." + QUEBRA + "Parâmetro auditado: entity1" + QUEBRA
		    + "Valor: teste anotação entidade." + QUEBRA + "Parâmetro auditado: entity2" + QUEBRA
		    + "Valor: teste anotação entidade.");
	}

	private void assertAudit(String string) {
		assertEquals(string, MessageDestination.getMessage());
	}
}
