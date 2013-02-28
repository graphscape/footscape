/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.HeaderItems;
import com.fs.uicommons.api.gwt.client.event.AutoLoginRequireEvent;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;

/**
 * @author wuzhen
 * 
 */
public class ClientStartedHandler extends UiHandlerSupport implements EventHandlerI<AfterClientStartEvent> {

	/**
	 * @param c
	 */
	public ClientStartedHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(AfterClientStartEvent e) {
		// start frwk view.
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		fc.open();
		fc.addHeaderItem(HeaderItems.USER_LOGIN);
		fc.addHeaderItem(HeaderItems.USER_LOGOUT);
		new AutoLoginRequireEvent(e.getSource()).dispatch();
	}

}
