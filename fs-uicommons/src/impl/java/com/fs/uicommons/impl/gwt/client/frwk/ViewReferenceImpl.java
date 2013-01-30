/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 15, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ViewReferenceImpl implements ViewReferenceI {

	private WidgetI managedWidget;

	private boolean selected;

	private BodyModelImpl body;

	private Path path;

	/**
	 * @param name
	 */
	public ViewReferenceImpl(Path path, WidgetI mw, BodyModelImpl bm) {
		this.path = path;
		this.managedWidget = mw;
		this.body = bm;
	}

	@Override
	public void select(boolean sel) {
		//
		this.selected = sel;
		this.body.selectView(this, sel);
		// TODO
	}

	@Override
	public boolean isSelect() {
		return this.selected;
	}

	@Override
	public WidgetI getManagedWidget() {
		return this.managedWidget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI#getPath()
	 */
	@Override
	public Path getPath() {
		return this.path;
	}

}
