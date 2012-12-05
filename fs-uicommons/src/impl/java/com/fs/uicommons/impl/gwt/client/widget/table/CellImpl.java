/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.RowI;
import com.fs.uicommons.impl.gwt.client.widget.table.support.TableHelper;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class CellImpl extends TableHelper implements TableI.CellI {

	protected RowI row;

	/** */
	public CellImpl(RowImpl r) {
		super(DOM.createTD(), r.getTable());
		this.row = r;
	}

	/* */
	@Override
	public CellI child(WidgetI w) {
		super.child(w);
		return this;

	}

}
