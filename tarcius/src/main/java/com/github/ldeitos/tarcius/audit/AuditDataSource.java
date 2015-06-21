package com.github.ldeitos.tarcius.audit;

import static org.apache.commons.collections4.MapUtils.unmodifiableMap;
import static org.apache.commons.collections4.QueueUtils.unmodifiableQueue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.github.ldeitos.tarcius.api.ParameterResolver;

/**
 * Audit data source representation containing:<br>
 * - Audit reference <br>
 * - Audited parameter values (tranlated by {@link ParameterResolver})
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class AuditDataSource {

	private String auditReference;

	private Map<String, Object> parameterValues = new HashMap<String, Object>();

	private Map<String, String> resolvedParameterValues = new HashMap<String, String>();

	private Queue<String> auditRefs = new LinkedList<String>();

	public AuditDataSource(String ref) {
		auditReference = ref;
	}

	public void addParameterValue(String key, Object parameter, String resolvedValue) {
		auditRefs.offer(key);
		resolvedParameterValues.put(key, resolvedValue);
		parameterValues.put(key, parameter);
	}

	public Map<String, String> getResolvedParameterValues() {
		return unmodifiableMap(resolvedParameterValues);
	}

	public Map<String, Object> getParameterValues() {
		return unmodifiableMap(parameterValues);
	}

	public String getAuditReference() {
		return auditReference;
	}

	/**
	 * @return FIFO queue of audited parameter references
	 */
	public Queue<String> getAuditParameterReferences() {
		return unmodifiableQueue(auditRefs);
	}

}
