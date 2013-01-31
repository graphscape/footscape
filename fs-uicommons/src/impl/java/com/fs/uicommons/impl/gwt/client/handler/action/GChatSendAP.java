/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class GChatSendAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public GChatSendAP(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		GChatControlI gc = ae.getSource().getClient(true).find(GChatControlI.class, true);
		gc.send();

	}

}
