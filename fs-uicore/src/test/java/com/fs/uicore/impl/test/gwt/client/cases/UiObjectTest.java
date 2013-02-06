/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import java.util.List;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu see <code>
 *         http://code.google.com/p/google-web-toolkit/source/browse/trunk/user/test/com/google/gwt/user/client/WindowTest.java?r=10807
 *         </code>
 */
public class UiObjectTest extends TestBase {

	public static final class MyParentObject extends UiObjectSupport {

		/**
		 * @param c
		 */
		public MyParentObject(ContainerI c) {
			super(c);
		}

	}

	public static final class MyChildObject extends UiObjectSupport {

		private String id;

		/**
		 * @param c
		 */
		public MyChildObject(String id, ContainerI c) {
			super(c);
			this.id = id;
		}

	}

	public UiObjectTest() {
		super(false);
	}

	public void testObject() {

		MyParentObject po = new MyParentObject(this.container);
		MyChildObject co1 = new MyChildObject("1", this.container);
		MyChildObject co2 = new MyChildObject("2", this.container);

		assertTrue("should empty now", po.getChildList(UiObjectI.class).isEmpty());

		po.child(co1);
		assertEquals("should contains 1 child", 1, po.getChildList(UiObjectI.class).size());

		po.child(co2);
		assertEquals("should contains 2 child", 2, po.getChildList(UiObjectI.class).size());

		co2.parent(null);
		List<UiObjectI> cL = po.getChildList(UiObjectI.class);
		assertEquals("should contains 1 child again", 1, cL.size());
		MyChildObject co11 = (MyChildObject) cL.get(0);
		assertEquals("not the same", co1.id, co11.id);

		assertNull("should remove from parent", co2.getParent());

	}
}
