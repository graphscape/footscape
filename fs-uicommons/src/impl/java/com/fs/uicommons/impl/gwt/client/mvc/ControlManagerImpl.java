package com.fs.uicommons.impl.gwt.client.mvc;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * 
 * @author wu
 * 
 */
public class ControlManagerImpl extends UiObjectSupport implements
		ControlManagerI {

	@Override
	public ControlManagerI addControl(ControlI c) {

		this.child(c);//
		return this;
	}

	@Override
	public <T extends ControlI> T getControl(Class<T> cls, String name,
			boolean force) {
		return this.getChild(cls, name, force);
	}

	@Override
	public <T extends ControlI> T getControl(Class<T> cls, boolean force) {
		return this.getChild(cls, null, force);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ControlManagerI#addControl(com.fs
	 * .uicore.api.gwt.client.ModelI,
	 * com.fs.uicommons.api.gwt.client.mvc.ControlI)
	 */
	@Override
	public ControlManagerI addControl(ModelI model, ControlI c) {
		c.model(model);
		return this.addControl(c);

	}

	/*
	 * @Override public ControlManagerI addControl(ModelI model, ViewI view,
	 * ControlI c) { if (!(view instanceof SelfManageI)) { throw new
	 * UiException("view must be SelfManageI"); } c.model(model);
	 * view.model(model); ((SelfManageI) view).manage();
	 * 
	 * return this.addControl(c);
	 * 
	 * }
	 */

}
