package com.github.ldeitos.util.factory;

@QStringTest("A")
public class AImpl implements StringInterface {

	@Override
	public String get() {
		return "A";
	}

}
