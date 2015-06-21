package com.github.ldeitos.tarcius.support;

import static com.github.ldeitos.tarcius.configuration.TranslateType.CUSTOM;
import static com.github.ldeitos.tarcius.configuration.TranslateType.JAXB_FORMATTED_XML;
import static com.github.ldeitos.tarcius.configuration.TranslateType.JAXB_JSON;
import static com.github.ldeitos.tarcius.configuration.TranslateType.JAXB_XML;

import java.util.Date;

import com.github.ldeitos.tarcius.api.annotation.Audit;
import com.github.ldeitos.tarcius.api.annotation.Audited;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

public class ToAudit {

	@Audit
	public void testRefMethodName() {

	}

	@Audit(auditRef = "especificRef")
	public void testDefinedRef() {

	}

	@Audit(auditRef = "parametrized")
	public void testParametrizedMethodDefinedRef(String par1) {

	}

	@Audit
	public void testParametrizedMethod(String par1) {

	}

	@Audit(auditRef = "parameterTest")
	public void testStringParam(@Audited(auditRef = "parName") String par1) {

	}

	@Audit(auditRef = "parameterTest2")
	public void testStringIntParam(@Audited(auditRef = "par1") String par1,
		@Audited(auditRef = "par2") int par2) {

	}

	@Audit(auditRef = "parameterTest")
	public void testFormattedDateIntParam(@Audited(auditRef = "par1", format = "yyyyMMdd") Date par1,
		@Audited(auditRef = "par2", format = "%05d") int par2) {

	}

	@Audit(auditRef = "parameterTest")
	public void testXML(@Audited(auditRef = "xmlPar", translator = JAXB_XML) Teste par) {

	}

	@Audit(auditRef = "parameterTest")
	public void testFormattedXML(@Audited(auditRef = "xmlPar", translator = JAXB_FORMATTED_XML) Teste par) {

	}

	@Audit(auditRef = "parameterTest")
	public void testJSON(@Audited(auditRef = "jsonPar", translator = JAXB_JSON) Teste par) {

	}

	@Audit(auditRef = "parameterTest")
	public void testCustomResolver(
	    @Audited(auditRef = "custom", translator = CUSTOM, customResolverQualifier = @CustomResolver("customTeste")) Teste par) {

	}

	@Audit(auditRef = "parameterTest")
	public void testInvalidCustomResolver(
	    @Audited(auditRef = "custom", translator = CUSTOM, customResolverQualifier = @CustomResolver("foo")) Teste par) {

	}

}
