package com.github.ldeitos.validators.util;

public class PathBuilder {
	private Path path;

	private Path lastPath;

	public PathBuilder() {
		super();
	}

	public PathBuilder add(String path) {
		addPath(new Path(path));
		return this;
	}

	public PathBuilder add(String path, String atKey) {
		addPath(new Path(path, atKey));
		return this;
	}

	public PathBuilder addAtKey(String atKey) {
		addPath(new Path(null, atKey));
		return this;
	}

	public PathBuilder add(String path, Integer atIndex) {
		addPath(new Path(path, atIndex));
		return this;
	}

	public PathBuilder addAtIndex(Integer atIndex) {
		addPath(new Path(null, atIndex));
		return this;
	}

	private void addPath(Path path) {
		if (this.path == null) {
			this.path = path;
		}

		if (lastPath != null) {
			lastPath.addNext(path);
		}

		lastPath = path;
	}

	public Path getPath() {
		return path;
	}

}