package com.github.ldeitos.validators.util;

public class Path {

	private final String path;

	private Path nextPath;

	private String key;

	private Integer index;

	Path(String path) {
		this.path = path;
	}

	Path(String path, String key) {
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

	public boolean hasIndex() {
		return index != null;
	}

	public boolean hasKey() {
		return key != null;
	}

	public String getPath() {
		return path;
	}

	public String getKey() {
		return key;
	}

	public Integer getIndex() {
		return index;
	}

}
