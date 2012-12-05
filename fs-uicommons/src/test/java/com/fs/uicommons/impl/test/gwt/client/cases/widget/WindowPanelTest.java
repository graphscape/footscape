/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.widget;

import com.fs.uicommons.api.gwt.client.widget.wpanel.WindowPanelWI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.WindowI;
import com.fs.uicore.api.gwt.client.commons.Rectangle;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class WindowPanelTest extends TestBase {

	WindowPanelWI tw;
	Size size;

	public void testTabber() {
		tw = wf.create(WindowPanelWI.class);

		tw.parent(this.root);// TODO assert attached.

		assertTrue("tw not attached", tw.isAttached());
		
		WindowI w = this.container.get(WindowI.class, true);

		size = Size.valueOf(401, 501);
		w.resizeTo(size);//

		this.delayTestFinish(10 * 1000);
	}

	protected void onResized() {

		Element ele = tw.getElement();
		ElementWrapper ew = new ElementWrapper(ele);

		Rectangle r2 = ew.getAbsoluteRectangle();

		Size size2 = r2.getSize();

		assertEquals("the windowPanel should follow the size of window", size,
				size2);
	}

}
