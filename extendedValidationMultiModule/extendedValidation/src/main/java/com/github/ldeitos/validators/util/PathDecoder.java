package com.github.ldeitos.validators.util;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.math.NumberUtils.toInt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.ldeitos.exception.InvalidPathConfigurationException;

/**
 * Decoder to build a {@link Path} instance based in a string representation of
 * a path, like "address[home].country.name", for example.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.9.0
 */
public class PathDecoder {
	private static final Pattern ITERABLE_PATH_PATTERN = Pattern.compile("^(\\w+)(\\[(.+)\\]|(\\((.+)\\)))");

	private static final Pattern INVALID_ITERABLE_PATH_PATTERN = Pattern.compile("\\w+\\[\\w+\\.\\w*\\]");

	private static final Pattern NUMERIC = Pattern.compile("\\d+");

	private static final Pattern INDEXED_ITERABLE = Pattern.compile("(\\(.+\\))");

	private static final int PATH_NAME = 1;

	private static final int ITERABLE_KEY = 3;

	private static final int ITERABLE_INDEX = 5;

	/**
	 * Decodes a string representation of path, like
	 * "address[home].country.name", to {@link Path} object.
	 *
	 * @param fullPath
	 *            String representation of a path.
	 * @return {@link Path} instance decoded from string representation of path.
	 *
	 * @since 0.9.0
	 */
	public static Path decodePath(String fullPath) {
		validateKeyFormat(fullPath);
		String[] paths = split(fullPath, ".");
		PathBuilder pathBuilder = new PathBuilder();

		for (String pathPart : paths) {
			pathBuilder.addPath(decodeSimplePath(pathPart));
		}

		return pathBuilder.getPath();
	}

	private static Path decodeSimplePath(String fullPath) {
		Path path = null;
		Matcher matcherIterable = ITERABLE_PATH_PATTERN.matcher(fullPath);

		if (matcherIterable.matches()) {
			String pathName = matcherIterable.group(PATH_NAME);
			Matcher matcherIndexed = INDEXED_ITERABLE.matcher(fullPath);

			if (matcherIndexed.find()) {
				String index = matcherIterable.group(ITERABLE_INDEX);
				validateIndexFormat(index);
				path = new Path(pathName, toInt(index));
			} else {
				String key = matcherIterable.group(ITERABLE_KEY);
				path = new Path(pathName, key);
			}
		} else {
			path = new Path(fullPath);
		}

		return path;
	}

	private static void validateIndexFormat(String iterable) {
		if (!NUMERIC.matcher(iterable).matches()) {
			InvalidPathConfigurationException.throwNew(format("[%s] - Invalid format to indexed "
				+ "iterable path, only numbers are allowed.", iterable));
		}
	}

	private static void validateKeyFormat(String fullPath) {
		Matcher matcher = INVALID_ITERABLE_PATH_PATTERN.matcher(fullPath);
		if (matcher.find()) {

			InvalidPathConfigurationException.throwNew(format("Invalid format to mapped iterable path: "
			    + "-> %s <- Maybe a \".\" used in key map value? (i.e: [not.use.dot])", fullPath));
		}
	}

	/**
	 * <p>
	 * Move iterable key or index to next path in path chain and identify the
	 * path as "shifted". Necessary because in fluent API to build constraint
	 * the key or index is applied in target leaf, not in original map or list.
	 * </p>
	 * <p>
	 * If input {@link Path} is marked as "shifted", nothing is done.
	 * </p>
	 *
	 * @param path
	 *            Root {@link Path} of path chain.
	 */
	static void shiftIterables(Path path) {
		if (path == null || path.isShifted()) {
			return;
		}
		Path nextPath = path.getNext();

		shiftIterables(nextPath);

		if (path.isIterable() && nextPath != null) {
			nextPath.setKey(path.getKey());
			nextPath.setIndex(path.getIndex());
			path.setKey(null);
			path.setIndex(null);
		}

		path.setShifted(true);
	}
}
