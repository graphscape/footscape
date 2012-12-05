/**
 * Jun 20, 2012
 */
package com.fs.commons.api;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public class Assert {

	public static void assertNull(Object obj, String msg) {
		assertTrue(obj == null, msg);
	}

	public static void assertNotNull(Object obj, String msg) {
		assertTrue(obj != null, msg);
	}

	public static void assertFalse(boolean b, String msg) {
		assertTrue(!b, msg);
	}

	public static void assertTrue(boolean b, String msg) {
		if (!b) {
			throw new FsException("" + msg);
		}
	}

}
