/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table.support;

import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.impl.gwt.client.widget.table.TableImpl;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class TableHelper extends LayoutSupport {

	protected TableImpl table;

	protected Element element;

	public TableHelper(Element ele, TableImpl t) {
		super(ele);
		this.table = t;
	}

	/**
	 * @return the table
	 */
	public TableImpl getTable() {
		return table;
	}
}
