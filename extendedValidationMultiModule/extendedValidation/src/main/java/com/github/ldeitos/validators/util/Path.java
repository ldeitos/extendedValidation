package com.github.ldeitos.validators.util;

import static java.lang.String.format;

/**
 * A path, or path chain, representation.<br>
 * - A single path have just a path name;<br>
 * - Iterable path have path name, key or index;<br>
 * - A path chain have a next path reference;
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class Path {

	private final String path;

	private Path nextPath;

	private Object key;

	private Integer index;

	private boolean shifted;

	Path(String path) {
		this.path = path;
	}

	Path(String path, Object key) {
		this(path);
		this.key = key;
	}

	Path(String path, Integer index) {
		this(path);
		this.index = index;
	}

	void addNext(Path path) {
		nextPath = path;
	}

	public Path getNext() {
		return nextPath;
	}

	public boolean isIterable() {
		return hasKey() || hasIndex();
	}

	public boolean isRootIterablePath() {
		return isIterable() && getPath() == null;
	}

	public boolean hasIndex() {
		return index != null;
	}

	public boolean hasKey() {
		return key != null;
	}

	public String getPath() {
		return path;
	}

	void setKey(Object key) {
		this.key = key;
	}

	public Object getKey() {
		return key;
	}

	void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getIndex() {
		return index;
	}

	public boolean hasNext() {
		return nextPath != null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(formatPath(this));
		Path nextPath = this.nextPath;

		while (nextPath != null) {
			sb.append(".").append(formatPath(nextPath));
			nextPath = nextPath.nextPath;
		}

		return sb.toString();
	}

	private String formatPath(Path pathInstance) {
		StringBuilder sb = new StringBuilder(pathInstance.getPath());

		if (pathInstance.getKey() != null) {
			sb.append(format("[%s]", pathInstance.getKey().toString()));
		} else if (pathInstance.getIndex() != null) {
			sb.append(format("(%d)", pathInstance.getIndex()));
		}

		return sb.toString();
	}

	boolean isShifted() {
		return shifted;
	}

	public void setShifted(boolean shifted) {
		this.shifted = shifted;
	}

}
