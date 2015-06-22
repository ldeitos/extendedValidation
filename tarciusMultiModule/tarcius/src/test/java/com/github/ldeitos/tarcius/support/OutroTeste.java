package com.github.ldeitos.tarcius.support;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OutroTeste {
	private String field;

	private Teste teste;

	public OutroTeste() {

	}

	public OutroTeste(Teste teste, String str) {
		field = str;
		this.teste = teste;
	}

	public String getField() {
		return field;
	}

	public Teste getTeste() {
		return teste;
	}
}
