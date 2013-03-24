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
public class TableWrapper extends ElementWrapper {

	/**
	 * @param ele
	 */
	public TableWrapper() {
		super(DOM.createTable());
		this.cellspacing(0);
		this.cellspading(0);
	}

	public TableWrapper cellspacing(int cs) {
		this.setAttribute("cellspacing", cs + "");
		return this;
	}

	public TableWrapper cellspading(int cs) {
		this.setAttribute("cellspading", cs + "");
		return this;
	}

	public TRWrapper insertTrBefore(TRWrapper trw) {
		TRWrapper rt = new TRWrapper();
		this.element.insertBefore(rt.getElement(), trw == null ? null : trw.getElement());
		return rt;

	}

	public TRWrapper addTr() {
		TRWrapper rt = new TRWrapper();
		this.append(rt);
		return rt;
	}

}
