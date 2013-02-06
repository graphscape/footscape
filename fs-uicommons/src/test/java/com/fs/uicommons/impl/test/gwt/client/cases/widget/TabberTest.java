/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.widget;

import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class TabberTest extends TestBase {

	public void testTabber() {
		TabberWI tw = wf.create(TabberWI.class);
		tw.parent(this.root);// TODO assert attached.
		TabWI tab1 = tw.addTab(Path.valueOf("tab1"));

		TabWI tab2 = tw.addTab(Path.valueOf("tab2"));

		assertTrue("tab1 not attached", tab1.isAttached());

		tab2._click();// tab2 will be selected.

		assertFalse("tab1 should not selected", tab1.isSelected());

		assertTrue("tab2 should be selcted", tab2.isSelected());

		PanelWI p1 = tab1.getPanel();

		PanelWI p2 = tab2.getPanel();
		// assertFalse("panel 1 should not visible", p1.isVisible());
		// assertTrue("panel 2 should visible", p2.isVisible());
	}

}
