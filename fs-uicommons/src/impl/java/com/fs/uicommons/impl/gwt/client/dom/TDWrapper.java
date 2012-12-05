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
public class TDWrapper extends ElementWrapper {

	/**
	 * @param ele
	 */
	public TDWrapper() {
		super(DOM.createTD());
	}

	public void setRowSpan(int rs) {
		this.setAttribute("rowspan", "" + rs);
	}
	
	public void setColspan(int cs){
		this.setAttribute("colspan", cs+"");
	}

}
