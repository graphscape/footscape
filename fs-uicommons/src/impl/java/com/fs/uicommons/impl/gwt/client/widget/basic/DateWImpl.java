/**
 * Jun 29, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.basic;

import com.fs.uicommons.api.gwt.client.widget.basic.DateWI;
import com.fs.uicommons.api.gwt.client.widget.support.WidgetSupport;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class DateWImpl extends WidgetSupport implements DateWI {

	/** */
	public DateWImpl(String name) {
		super(name, DOM.createLabel());
	}

	@Override
	protected void processModelDefaultValue(ValueWrapper vw) {

		Element ele = this.getElement();

		DateData sd = (DateData) vw.getValue();
		String str = DateUtil.format(sd, false);
		ele.setInnerText(str);//

		ele.setTitle(str);

	}
}
