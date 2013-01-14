/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 14, 2013
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class UiHandlerSupport {

	protected EndPointI getEndpoint(ActionEvent ae) {
		return ae.getSource().getClient(true).getEndpoint();
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(ActionEvent ae, MsgWrapper req) {

		ae.getSource().getClient(true).getEndpoint().sendMessage(req);//
	}

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
