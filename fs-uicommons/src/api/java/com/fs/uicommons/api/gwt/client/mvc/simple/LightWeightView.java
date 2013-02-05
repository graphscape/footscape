/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.simple;

import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu display model as text
 */
public class LightWeightView extends ViewSupport {

	/**
	 * @param ctn
	 */
	public LightWeightView(ContainerI ctn) {
		this(ctn, null);
	}

	public LightWeightView(ContainerI c, String name) {
		this(c, name, DOM.createDiv());
	}

	public LightWeightView(ContainerI c, String name, Element ele) {
		super(c, name, ele);
	}

}
