/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI;
import com.fs.uicommons.impl.gwt.client.widget.table.support.TableHelper;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class RowImpl extends TableHelper implements TableI.RowI {

	protected BodyImpl body;

	/** */
	public RowImpl(BodyImpl t) {
		super(DOM.createTR(), t.getTable());
		this.body = t;
	}

	/* */
	@Override
	public CellI createCell() {
		CellI rt = new CellImpl(this);
		this.factory.initilize(rt, new SimpleModel(""));
		this.child(rt);
		return rt;

	}

}
