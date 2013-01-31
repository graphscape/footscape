/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.HeaderItems;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicommons.impl.gwt.client.frwk.login.LoginModel;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public class HeaderItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	/**
	 * @param c
	 */
	public HeaderItemHandler(ContainerI c) {
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
		Path p = t.getPath();
		HeaderModelI hm = this.getRootModel().find(HeaderModelI.class, true);
		ItemModel im = hm.getItem(p, true);

		if (p.getName().equals(HeaderItems.USER_LOGIN.getName())) {
			LoginControlI lc = this.getControl(LoginControlI.class, true);
			lc.openLoginView();
		}
	}

}
