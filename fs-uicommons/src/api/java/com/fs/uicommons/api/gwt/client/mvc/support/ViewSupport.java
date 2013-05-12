/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class ViewSupport extends LayoutSupport implements ViewI {

	public ViewSupport(ContainerI c, Element ele) {
		this(c, null, ele);
	}

	public ViewSupport(ContainerI c, String name, Element ele) {
		super(c, name, ele);
	}

	@Override
	public void clickAction(Path a) {
		throw new UiException("TODO");
	}

	/**
	 * @param name
	 */
	protected void dispatchActionEvent(Path name) {
		ActionEvent ae = this.newActionEvent(name);
		this.beforeActionEvent(ae);
		ae.dispatch();
	}

	//
	protected ActionEvent newActionEvent(Path aname) {
		ActionEvent rt = new ActionEvent(this, (aname));

		return rt;
	}

	protected void beforeActionEvent(ActionEvent ae) {

	}

	protected EndPointI getEndpoint() {
		return this.getClient(true).getEndpoint(true);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getEndpoint().sendMessage(req);
	}

	protected void sendMessage(MessageData req) {
		this.getEndpoint().sendMessage(req);
	}

	protected <T extends ControlI> T getControl(Class<T> cls, boolean force) {
		return this.getControlManager().getControl(cls, force);
	}

	protected ControlManagerI getControlManager() {

		return this.getClient(true).getChild(ControlManagerI.class, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ViewI#addErrorInfo(com.fs.uicore.
	 * api.gwt.client.data.ErrorInfosData)
	 */
	@Override
	public void addErrorInfo(ErrorInfosData eis) {

	}

	@Override
	public void clearErrorInfo() {

	}
}
