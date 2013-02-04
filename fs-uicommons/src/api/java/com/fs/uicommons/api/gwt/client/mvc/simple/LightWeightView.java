/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.simple;

import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
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
		this(null, ctn);
	}

	public LightWeightView(String name, ContainerI ctn) {
		super(name, DOM.createDiv(), ctn);
	}

	public LightWeightView(String name, ContainerI ctn, ModelI md) {
		super(name, DOM.createDiv(), ctn, md);
	}

	public LightWeightView(String name, Element ele, ContainerI ctn) {
		super(name, ele, ctn);
	}

	public LightWeightView(String name, Element ele, ContainerI ctn, ModelI md) {
		super(name, ele, ctn, md);

	}

}
