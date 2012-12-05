/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.RowI;
import com.fs.uicommons.impl.gwt.client.widget.table.support.TableHelper;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class BodyImpl extends TableHelper implements TableI.BodyI {

	/** */
	public BodyImpl(TableImpl t) {
		super(DOM.createTBody(), t);

	}

	/* */
	@Override
	public RowI createRow() {
		RowI rt = new RowImpl(this);
		this.factory.initilize(rt, new SimpleModel(""));
		this.child(rt);//
		return rt;

	}

}
