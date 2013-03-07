/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 7, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 *
 */
public class ExpMessageView extends ViewSupport{

	
	/**
	 * @param c
	 * @param ele
	 */
	public ExpMessageView(ContainerI c, ExpMessage msg) {
		super(c, DOM.createDiv());
		String text = msg.getBody();
		this.element.setInnerText(text);
	}

}
