/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wuzhen
 * 
 */
public class LogoutHeaderItemHandler extends UiHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	/**
	 * @param c
	 */
	public LogoutHeaderItemHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(HeaderItemEvent t) {
		EndPointI e = this.getEndpoint();
		UserInfo ui = e.getUserInfo();
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		HeaderViewI hv = fc.getHeaderView();
		// just close the connect to server.
		e.close();//
	}

}
