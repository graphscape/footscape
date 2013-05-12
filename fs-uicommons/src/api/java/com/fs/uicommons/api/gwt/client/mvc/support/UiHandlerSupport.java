/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 14, 2013
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class UiHandlerSupport {

	protected ContainerI container;

	public UiHandlerSupport(ContainerI c) {
		this.container = c;
	}

	protected EndPointI getEndpoint() {
		return this.getClient(true).getEndpoint(true);
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getEndpoint(true).sendMessage(req);//
	}

	protected ControlManagerI getControlManager() {
		return this.getClient(true).getChild(ControlManagerI.class, true);
	}

	protected UiClientI getClient(boolean force) {
		return this.container.get(UiClientI.class, force);
	}

	protected RootI getRootView() {
		return this.getClient(true).getRoot();
	}

	protected <T extends ControlI> T getControl(Class<T> cls, boolean force) {
		return this.getControlManager().getControl(cls, force);
	}
}
