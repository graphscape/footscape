/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table.support;

import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicommons.impl.gwt.client.widget.table.TableImpl;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class TableHelper extends LayoutSupport {

	protected TableImpl table;

	public TableHelper(ContainerI c, Element ele, TableImpl t) {
		super(c, ele);
		this.table = t;
	}

	/**
	 * @return the table
	 */
	public TableImpl getTable() {
		return table;
	}
}
