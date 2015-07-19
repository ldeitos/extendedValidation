package com.github.ldeitos.test.base.stubs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InvalidMappedConstraint(groups = { GrupoInvalidMapped.class })
@InvalidListConstraint(groups = { GrupoInvalidList.class })
@UserConstraintFullPathListExtended(groups = { GrupoClassLevelUserFullPathListExtended.class })
@UserConstraintDefault(groups = { GrupoClassLevelUserDefault.class })
@UserConstraintExtended(groups = { GrupoClassLevelUserExtended.class })
@UserConstraintFullPathExtended(groups = { GrupoClassLevelUserFullPathExtended.class })
public class User {
	private Map<String, Address> addresses = new HashMap<String, Address>();

	private List<Address> listAddesses;

	@PropertyConstraintExtended(groups = { GrupoPropertyLevelExtended.class })
	@PropertyConstraintDefault(groups = { GrupoPropertyLevelDefault.class })
	@PropertyMappedConstraintDefault(groups = { GrupoPropertyLevelMappedDefault.class })
	@PropertyMappedConstraintExtended(groups = { GrupoPropertyLevelMappedExtended.class })
	public Map<String, Address> getAddresses() {
		return addresses;
	}

	public List<Address> getListAddesses() {
		return listAddesses;
	}
}
