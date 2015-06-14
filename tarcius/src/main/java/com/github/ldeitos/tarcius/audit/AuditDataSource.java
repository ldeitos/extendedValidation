package com.github.ldeitos.tarcius.audit;

import static org.apache.commons.collections4.MapUtils.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

public class AuditDataSource {

	private String auditReference;

	private Map<String, String> resolvedParameterValues = new HashMap<String, String>();

	public AuditDataSource(String ref) {
		auditReference = ref;
	}

	public void addParameterValue(String key, String value) {
		resolvedParameterValues.put(key, value);
	}

	public Map<String, String> getResolvedParameterValues() {
		return unmodifiableMap(resolvedParameterValues);
	}

	public String getAuditReference() {
		return auditReference;
	}

}
