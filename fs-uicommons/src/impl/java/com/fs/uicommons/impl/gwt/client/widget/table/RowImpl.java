/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI;
import com.fs.uicommons.impl.gwt.client.widget.table.support.TableHelper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class RowImpl extends TableHelper implements TableI.RowI {

	protected BodyImpl body;

	/** */
	public RowImpl(ContainerI c, BodyImpl t) {
		super(c,DOM.createTR(), t.getTable());
		this.body = t;
	}

	/* */
	@Override
	public CellI createCell() {
		CellI rt = new CellImpl(this.container, this);
		this.child(rt);
		return rt;

	}

}
