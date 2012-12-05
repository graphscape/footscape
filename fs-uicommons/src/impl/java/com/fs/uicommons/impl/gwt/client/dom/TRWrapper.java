/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package com.fs.uicommons.impl.gwt.client.dom;

import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class TRWrapper extends ElementWrapper {

	/**
	 * @param ele
	 */
	public TRWrapper() {
		super(DOM.createTR());
	}

	public TDWrapper addTd() {
		TDWrapper rt = new TDWrapper();
		this.append(rt);
		return rt;
	}
}
