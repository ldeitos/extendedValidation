package com.github.ldeitos.validator.stubs;

import java.util.HashMap;
import java.util.Map;

@UserConstraintDefault(groups = { GrupoClassLevelUserDefault.class })
@UserConstraintExtended(groups = { GrupoClassLevelUserExtended.class })
public class User {
	private Map<String, Address> addresses = new HashMap<String, Address>();

	@PropertyConstraintExtended(groups = { GrupoPropertyLevelExtended.class })
	@PropertyConstraintDefault(groups = { GrupoPropertyLevelDefault.class })
	@PropertyMappedConstraintDefault(groups = { GrupoPropertyLevelMappedDefault.class })
	@PropertyMappedConstraintExtended(groups = { GrupoPropertyLevelMappedExtended.class })
	public Map<String, Address> getAddresses() {
		return addresses;
	}
}
