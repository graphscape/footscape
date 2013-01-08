/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class IncomingCrNotifyMH extends NotifyMH {

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(MsgWrapper t) {
		MessageData msg = t.getMessage();
		CooperControlI cc = this.getControl(t, CooperControlI.class, true);
		cc.refreshIncomingCr(null);
	}

}
