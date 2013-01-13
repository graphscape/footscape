/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.api.gwt.client.support;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class HandlerSupport {

	protected ControlManagerI getControlManager(Event t) {
		return t.getSource().getClient(true).getChild(ControlManagerI.class, true);
	}

	protected UiClientI getClient(Event t) {
		return t.getSource().getClient(true);

	}

	protected <T extends ModelI> T getModel(Event t, Class<T> cls, boolean force) {
		return this.getClient(t).getRootModel().find(cls, force);
	}

	protected <T extends ControlI> T getControl(Event t, Class<T> cls, boolean force) {
		return this.getControlManager(t).getControl(cls, force);
	}
}
