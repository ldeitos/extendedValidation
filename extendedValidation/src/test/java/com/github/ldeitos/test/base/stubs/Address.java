package com.github.ldeitos.test.base.stubs;

@AddressConstraintDefault(groups = { GrupoAddressClassLevelDefault.class })
@AddressConstraintExtended(groups = { GrupoAddressClassLevelExtended.class })
public class Address {
	private String street;

	private Country country;

	public String getStreet() {
		return street;
	}

	public Country getCountry() {
		return country;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
