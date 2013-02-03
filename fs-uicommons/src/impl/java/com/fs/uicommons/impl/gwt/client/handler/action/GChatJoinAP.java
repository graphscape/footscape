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
public class GChatJoinAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public GChatJoinAP(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent t) {
		//
		// ep.s
		String gid = (String) t.getProperty("groupId", true);
		GChatControlI gc = t.getSource().getClient(true).find(GChatControlI.class, true);
		gc.join(gid);

	}

}
