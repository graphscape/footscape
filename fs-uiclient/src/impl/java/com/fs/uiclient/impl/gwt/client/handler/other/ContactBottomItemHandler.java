/**
 *  Jan 31, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.google.gwt.user.client.Window;

/**
 * @author wuzhen
 * 
 */
public class ContactBottomItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	/**
	 * @param c
	 */
	public ContactBottomItemHandler(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(HeaderItemEvent t) {

		MainControlI mc = this.getControl(MainControlI.class, true);
		//mc.openSignup();//
		String url = this.getClient(true).getParameter(UiClientConstants.PK_URL_CONTACT, false);
		Window.open(url, "_blank", "");
	}

}
