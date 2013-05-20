/**
 *  Jan 31, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.google.gwt.user.client.Window;

/**
 * @author wuzhen
 * 
 */
public class CreateHeaderItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	/**
	 * @param c
	 */
	public CreateHeaderItemHandler(ContainerI c) {
		super(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(HeaderItemEvent t) {
		UserInfo ui = this.getEndpoint().getUserInfo();
		if (ui.isAnonymous()) {
			boolean ok = Window.confirm("Please login before create! Open login view?");

			if (ok) {
				this.getControl(MainControlI.class, true).openLoginView(true);
			}

			return;
		}

		MainControlI mc = this.getControl(MainControlI.class, true);
		ExpEditViewI ev = mc.openExpEditView();
	}

}
