package com.github.ldeitos.validation.impl.util;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;

public class ParameterUtilTest extends BaseTest {
	private static final String PAR1 = "par1";
	private static final String PAR2 = "par2";
	private static final String PAR3 = "par3";
	private static final String PAR4 = "par4";

	private static final String VAL1 = "tar: batata: No such file or directory " + 
			"tar: error exit delayed from previous errors " + 
			"command terminated with non-zero exit code: Error " + 
			"executing in Docker Container: 1";
	private static final String VAL2 = "parametro com '=' no texto= duas vezes";
	private static final String VAL3 = "parametro com ':' e '=' no texto";
	private static final String VAL4 = "!@#$";

	@Test
	public void testFormatedOutput() {
		Map<String, Object> map = ParameterUtils.buildParametersMap(
				PAR1.concat("=").concat(VAL1),
				PAR2.concat("=").concat(VAL2), 
				PAR3.concat("=").concat(VAL3), 
				PAR4.concat("=").concat(VAL4));
		assertEquals(VAL1, map.get(PAR1));
		assertEquals(VAL2, map.get(PAR2));
		assertEquals(VAL3, map.get(PAR3));
		assertEquals(VAL4, map.get(PAR4));
	}

}
