package com.github.ldeitos.tarcius.audit;

import static org.apache.commons.collections4.MapUtils.unmodifiableMap;
import static org.apache.commons.collections4.QueueUtils.unmodifiableQueue;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.enterprise.inject.Alternative;

import org.apache.commons.lang.RandomStringUtils;

/**
 * A request scoped context containing all audit entries collected.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@Alternative
public class AuditContext {

	private static final int DEFAULT_ID_SIZE = 6;

	private final Date contextCreateInstant = new Date();

	private final String id;

	private Map<String, AuditDataSource> auditEntries = new HashMap<String, AuditDataSource>();

	private Queue<String> auditRefs = new LinkedList<String>();

	/**
	 * Default constructor defines size 6 to random string id generated to
	 * context.
	 */
	public AuditContext() {
		this(DEFAULT_ID_SIZE);
	}

	/**
	 * @param idSize
	 *            Size to random string id generated to context.
	 */
	public AuditContext(int idSize) {
		id = RandomStringUtils.random(idSize, true, true);
	}

	public void addAuditEntry(String ref, AuditDataSource entry) {
		auditRefs.offer(ref);
		auditEntries.put(ref, entry);
	}

	/**
	 * @return FIFO queue of audited references
	 */
	public Queue<String> getAuditReferences() {
		return unmodifiableQueue(auditRefs);
	}

	/**
	 * @return Map of audit entries. Key is reference string of audit point.
	 */
	public Map<String, AuditDataSource> getAuditEntries() {
		return unmodifiableMap(auditEntries);
	}

	/**
	 * @return Random string to identify a particular audit context instance.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return {@link Date} instance to represent context creation moment.
	 */
	public Date getContextCreateInstant() {
		return contextCreateInstant;
	}
}
