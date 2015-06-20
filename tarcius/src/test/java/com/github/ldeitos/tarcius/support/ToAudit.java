package com.github.ldeitos.tarcius.support;

import static com.github.ldeitos.tarcius.configuration.TranslateType.JAXB_JSON;
import static com.github.ldeitos.tarcius.configuration.TranslateType.JAXB_XML;

import java.util.Date;

import com.github.ldeitos.tarcius.api.annotation.Audit;
import com.github.ldeitos.tarcius.api.annotation.Audited;

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

	@Audit(auditRef = "parameterTest")
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
	public void testJSON(@Audited(auditRef = "jsonPar", translator = JAXB_JSON) Teste par) {

	}

}
