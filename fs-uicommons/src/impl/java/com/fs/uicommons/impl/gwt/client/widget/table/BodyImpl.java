/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.RowI;
import com.fs.uicommons.impl.gwt.client.widget.table.support.TableHelper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class BodyImpl extends TableHelper implements TableI.BodyI {

	/** */
	public BodyImpl(ContainerI c, TableImpl t) {
		super(c, DOM.createTBody(), t);

	}

	/* */
	@Override
	public RowI createRow() {
		RowI rt = new RowImpl(this.container, this);
		this.child(rt);//
		return rt;

	}

}
