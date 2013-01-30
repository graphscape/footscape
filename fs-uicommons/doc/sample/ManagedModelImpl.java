/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 15, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.manage.ViewReferenceI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ViewReferenceImpl extends ModelSupport implements ViewReferenceI {

	private WidgetI managedWidget;

	/**
	 * @param name
	 */
	public ViewReferenceImpl(String name, WidgetI mw) {
		super(name);
		this.managedWidget = mw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagedI#show()
	 */
	@Override
	public void select(boolean sel) {
		// listen by the center controller
		this.setDefaultValue(sel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagedI#isVisible()
	 */
	@Override
	public boolean isSelect() {
		return this.getDefaultValue(Boolean.TRUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagedI#getWidget()
	 */
	@Override
	public WidgetI getManagedWidget() {
		return this.managedWidget;
	}

}
