/**
 * Jun 29, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.basic;

import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.support.WidgetSupport;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class LabelImpl extends WidgetSupport implements LabelI {

	/** */
	public LabelImpl(String name) {
		super(name,DOM.createLabel());
	}

	@Override
	protected void processModelDefaultValue(ValueWrapper vw){

		Element ele = this.getElement();

		String sd = (String) vw.getValue();
		ele.setInnerText(sd);//
		ele.setTitle(sd);

	}
}
