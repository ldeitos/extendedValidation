package com.github.ldeitos.util.factory;

@QIntegerTest(1)
public class OneImpl implements IntegerInterface {

	@Override
	public Integer get() {
		return 1;
	}

}
