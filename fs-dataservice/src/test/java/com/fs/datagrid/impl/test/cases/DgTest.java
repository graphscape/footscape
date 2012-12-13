/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.test.cases;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
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
		//
		DgQueueI<Integer> q2 = this.dg.getQueue("test2");
		q2.offer(1);
		Integer i = q2.take();
		assertEquals((Integer) 1, i);
		//
		DgQueueI<PropertiesI<Object>> q3 = this.dg.getQueue("test3");
		PropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperty("key1", "string1");
		pts.setProperty("key2", 2);
		q3.offer(pts);

		PropertiesI<Object> pts2 = q3.take();
		assertEquals(pts, pts2);

	}
}
