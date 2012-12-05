/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 2, 2012
 */
package com.fs.uiclient.impl.gwt.client.util;

import com.fs.uicore.api.gwt.client.SimpleValueDeliverI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;

/**
 * @author wu
 * 
 */
public class ElementAttributeVS implements
		SimpleValueDeliverI.ValueSourceI<String> {

	protected ElementWrapper elementWrapper;

	protected String attribute;

	public ElementAttributeVS(ElementWrapper ew, String att) {
		this.elementWrapper = ew;
		this.attribute = att;
	}

	/*
	 * Dec 2, 2012
	 */
	@Override
	public String getValue() {
		//
		return this.elementWrapper.getAttribute(this.attribute);
	}

	/*
	 * Dec 2, 2012
	 */
	@Override
	public void setValue(String x) {
		this.elementWrapper.setAttribute(this.attribute, x);
	}

}
