/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.test.cases;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.datagrid.api.wrapper.PropertiesDW;
import com.fs.datagrid.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class DgTest extends TestBase {

	public static class ValueWrapper extends PropertiesDW {

		public ValueWrapper() {
			super();
		}

		/**
		 * @param t
		 */
		public ValueWrapper(PropertiesI<Object> t) {
			super(t);
		}

		public void setString(String s) {
			this.setProperty("string", s);
		}

		public String getString() {
			return (String) this.getProperty("string");
		}

		public void setInteger(Integer v) {
			this.setProperty("integer", v);
		}

		public Integer getInteger() {
			return (Integer) this.getProperty("integer");
		}
	}

	public void testDgMapWrapper() {
		ValueWrapper vw = new ValueWrapper();
		vw.setInteger(1);
		vw.setString("stringValue");

		DgMapI<String, PropertiesI<Object>> map = this.dg
				.getMap("testMapWrapper");
		map.put("key1", vw.getTarget());

		DgMapI<String, ValueWrapper> map2 = this.dg.getMap("testMapWrapper",
				ValueWrapper.class, ValueWrapper.class);
		ValueWrapper saved = map2.getValue("key1");
		assertNotNull("not saved", saved);
		assertEquals("saved value not same as original", vw, saved);

	}

	public void xtestDg() {
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
