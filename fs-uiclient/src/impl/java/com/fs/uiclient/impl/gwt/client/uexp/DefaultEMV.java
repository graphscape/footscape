/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 7, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class DefaultEMV extends ExpMessageView {
	/**
	 * @param c
	 * @param ele
	 */
	public DefaultEMV(ContainerI c, ExpMessage msg) {
		super(c, msg);
		String text = msg.toString();
		this.element.setInnerText(text);
	}

}
