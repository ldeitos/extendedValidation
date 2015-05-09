package com.github.ldeitos.util.factory;

@QIntegerTest(2)
public class TwoImpl implements IntegerInterface {

	@Override
	public Integer get() {
		return 2;
	}

}
