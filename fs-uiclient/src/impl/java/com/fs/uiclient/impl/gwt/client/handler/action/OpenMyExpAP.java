/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Cause;

/**
 * @author wu
 */
public class OpenMyExpAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public OpenMyExpAP(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(ActionEvent ae) {
		String expId = (String) ae.getProperty("expId", true);
		MainControlI mc = this.getControl(MainControlI.class, true);
		mc.openMyExp(Cause.valueOf("openMyExpAP"),expId, true);
	}

}
