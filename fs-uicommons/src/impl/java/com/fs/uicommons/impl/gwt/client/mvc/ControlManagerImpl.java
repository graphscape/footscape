package com.fs.uicommons.impl.gwt.client.mvc;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * 
 * @author wu
 * 
 */
public class ControlManagerImpl extends UiObjectSupport implements ControlManagerI {

	/**
	 * @param c
	 */
	public ControlManagerImpl(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ControlManagerI addControl(ControlI c) {

		this.child(c);//
		return this;
	}

	@Override
	public <T extends ControlI> T getControl(Class<T> cls, String name, boolean force) {
		return this.getChild(cls, name, force);
	}

	@Override
	public <T extends ControlI> T getControl(Class<T> cls, boolean force) {
		return this.getChild(cls, null, force);
	}

}
