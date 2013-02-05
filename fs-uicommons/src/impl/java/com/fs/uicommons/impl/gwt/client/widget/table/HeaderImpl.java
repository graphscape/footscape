/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.table;

import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.HeaderI;
import com.fs.uicommons.impl.gwt.client.widget.table.support.TableHelper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class HeaderImpl extends TableHelper implements TableI.HeaderI {

	protected String name;

	/** */
	public HeaderImpl(ContainerI c, TableImpl t, String name) {
		super(c, DOM.createTH(), t);
		this.name = name;
	}

	/* */
	@Override
	public String getName() {

		return this.name;

	}

	/* */
	@Override
	public HeaderI child(WidgetI w) {
		super.child(w);
		return this;

	}

}
