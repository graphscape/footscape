/**
 * Jun 13, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.list;

import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.google.gwt.user.client.DOM;

/**
 * @author wuzhen
 * 
 */
public class ListImpl extends LayoutSupport implements ListI {

	/*
	 * Grid
	 */

	/** */
	public ListImpl(String name) {
		super(name, DOM.createDiv());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.list.ListI#getSize()
	 */
	@Override
	public int getSize() {
		return this.getChildWidgetList().size();
	}

}
