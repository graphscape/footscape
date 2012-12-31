/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.test.cases;

import junit.framework.TestCase;

import com.fs.commons.api.struct.Node;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.struct.Tree;

/**
 * @author wuzhen
 * 
 */
public class TreeTest extends TestCase {

	public void testTree() {
		Tree t = Tree.newInstance();
		Path pc = Path.valueOf("/a/b/c", '/');
		Path pb = pc.getParent();
		Path pa = pb.getParent();
		Node c = t.addNode(pc);
		assertEquals("c", c.getName());
		Node b = t.getNode(pb);
		assertEquals("b", b.getName());
		Node a = t.getNode(pa);
		assertEquals("a", a.getName());

		
	}
}
