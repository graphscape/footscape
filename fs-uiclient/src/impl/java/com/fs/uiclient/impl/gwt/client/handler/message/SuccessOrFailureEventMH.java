/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.event.FailureMessageEvent;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class SuccessOrFailureEventMH extends MHSupport {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(MsgWrapper t) {
		MessageData res = t.getMessage();
		Path p = res.getPath();
		if ("success".equals(p.getName())) {
			new SuccessMessageEvent(t.getChannel(), res).dispatch();
		} else if ("failure".equals(p.getName())) {
			new FailureMessageEvent(t.getChannel(), res).dispatch();
		}
	}

}
