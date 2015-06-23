package com.github.ldeitos.tarcius.support;

import com.github.ldeitos.tarcius.api.annotation.Audited;

@Audited(auditRef = "entity")
public class OutroTeste {

	public OutroTeste() {

	}

	@Override
	public String toString() {
		return "teste anotação entidade.";
	}

}
