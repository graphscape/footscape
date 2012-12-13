/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.test.cases;

import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.datagrid.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class DgTest extends TestBase {

	public void testDg() {
		DgQueueI<String> q = this.dg.getQueue("test1");

		q.offer("1");
		String s = q.take();
		assertEquals("1", s);
	}
}
