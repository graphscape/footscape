/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 10, 2012
 */
package com.fs.uicore.impl.test.gwt.client.widget;

import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.support.WidgetBase;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class TestWidget extends WidgetBase implements TestWI {

	/**
	 * @param ele
	 */
	public TestWidget(String name) {
		super(name,DOM.createDiv());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.support.WidgetBase#doUpdate(com.fs.uicore
	 * .api.gwt.client.ModelI)
	 */
	@Override
	public void processModelDefaultValue(ValueWrapper e) {
		super.processModelDefaultValue(e);

		String txt = "" + e.getValue();
		this.element.setInnerText(txt);
	}

}
