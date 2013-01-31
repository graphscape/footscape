/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class OpenChatRoomAP extends ActionHandlerSupport {

	public OpenChatRoomAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		String actId = (String) ae.getProperty("actId", true);

		GChatControlI gc = this.getControl(GChatControlI.class, true);

		gc.join(actId);

	}

}
