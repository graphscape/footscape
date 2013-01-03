/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class PathTest extends TestCase {

	public void testPath() {
		Path root = Path.valueOf(new ArrayList<String>());
		assertTrue("", root.isRoot());

		Path p1 = Path.valueOf("p1");

		Path p2 = Path.valueOf("p1/p2");
		assertTrue(p2 + " should be the sub path of :" + p1, p1.isSubPath(p2));

		Path p31 = Path.valueOf("/p1/p2/p3", '/');
		assertEquals(3, p31.size());
		assertTrue(p31 + " should be the sub path of :" + p2, p1.isSubPath(p31));

		Path p32 = Path.valueOf("p1/p2/p3");
		assertEquals(p31, p32);

		assertFalse("p31 should not subpath of p32,it's same", p31.isSubPath(p32));
		assertTrue("p31 should subpath of p32,it's same", p31.isSubPath(p32, true));
		
		assertTrue("root's subpath",root.isSubPath(p1));
		assertTrue("root's subpath",root.isSubPath(p2));
		assertTrue("root's subpath",root.isSubPath(p1,true));
		assertTrue("root's subpath",root.isSubPath(p2,true));
		

	}
}
