/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 10, 2012
 */
package com.fs.uicore.impl.test.gwt.client;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf.CheckerSupport;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.fs.uicore.api.gwt.client.support.WidgetCreaterSupport;
import com.fs.uicore.impl.test.gwt.client.widget.TestWI;
import com.fs.uicore.impl.test.gwt.client.widget.TestWidget;

/**
 * @author wu
 * 
 */
public class UiCoreTestGPI implements GwtSPI {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.spi.GwtSPI#active(com.fs.uicore.api.gwt.
	 * client.ContainerI)
	 */
	@Override
	public void active(ContainerI c) {

		InstanceOf.addChecker(new CheckerSupport(TestWI.class) {

			@Override
			public boolean isInstance(Object o) {
				return o instanceof TestWI;
			}
		});

		c.get(WidgetFactoryI.class, true).addCreater(
				new WidgetCreaterSupport<TestWI>(TestWI.class) {

					@Override
					public TestWI create(String name) {
						// TODO Auto-generated method stub
						return new TestWidget(name);
					}
				});
	}

}
