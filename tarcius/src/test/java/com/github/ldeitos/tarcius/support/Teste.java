package com.github.ldeitos.tarcius.support;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Teste {
	private String field;

	public Teste() {

	}

	public Teste(String str) {
		field = str;
	}

	public String getField() {
		return field;
	}
}
