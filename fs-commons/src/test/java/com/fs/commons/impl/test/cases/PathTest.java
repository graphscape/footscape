/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.test.cases;

import junit.framework.TestCase;

import com.fs.commons.api.struct.Path;

/**
 * @author wuzhen
 * 
 */
public class PathTest extends TestCase {

	public void testSPI() throws Exception {
		Path path1 = Path.valueOf("/a/b/c/d");
		Path path2 = Path.valueOf("b/c");

		assertTrue(path1.contains(path2));

	}

}
