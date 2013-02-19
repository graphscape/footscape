/**
 * Jul 16, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.impl.test.gwt.client.widget.TestWI;

/**
 * @author wu
 * 
 */
public class WidgetFactoryTest extends TestBase {
	
	public WidgetFactoryTest(){
		super(false);
	}
	
	public void testWidgetFactory() {

		WidgetFactoryI wf = this.factory.getContainer().get(
				WidgetFactoryI.class, true);
		TestWI tw = wf.create(TestWI.class);
		
	}
}
