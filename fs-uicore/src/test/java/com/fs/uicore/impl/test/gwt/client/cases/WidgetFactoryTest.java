/**
 * Jul 16, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class WidgetFactoryTest extends TestBase {
	public void testWidgetFactory() {

		WidgetFactoryI wf = this.factory.getContainer().get(
				WidgetFactoryI.class, true);

	}
}
