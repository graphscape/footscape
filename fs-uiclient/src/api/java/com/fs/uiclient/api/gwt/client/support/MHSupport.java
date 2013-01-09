/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.api.gwt.client.support;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public abstract class MHSupport implements MessageHandlerI<EndpointMessageEvent> {

	protected ControlManagerI getControlManager(EndpointMessageEvent t) {
		return t.getSource().getClient(true).getChild(ControlManagerI.class, true);
	}

	protected UiClientI getClient(EndpointMessageEvent t) {
		return t.getSource().getClient(true);

	}

	protected <T extends ModelI> T getModel(EndpointMessageEvent t, Class<T> cls, boolean force) {
		return this.getClient(t).getRootModel().find(cls, force);
	}

	protected <T extends ControlI> T getControl(EndpointMessageEvent t, Class<T> cls, boolean force) {
		return this.getControlManager(t).getControl(cls, force);
	}
}
