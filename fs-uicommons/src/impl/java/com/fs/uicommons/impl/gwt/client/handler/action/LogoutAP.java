/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class LogoutAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public LogoutAP(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param c
	 */

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		this.getEndpoint().logout();//
	}

}
