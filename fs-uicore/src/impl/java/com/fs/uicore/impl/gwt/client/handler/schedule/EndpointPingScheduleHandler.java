/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 11, 2013
 */
package com.fs.uicore.impl.gwt.client.handler.schedule;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.ScheduleEvent;

/**
 * @author wu
 * 
 */
public class EndpointPingScheduleHandler implements EventHandlerI<ScheduleEvent> {

	private EndPointI endpoint;

	public EndpointPingScheduleHandler(EndPointI ep) {
		this.endpoint = ep;
	}

	/*
	 * Jan 11, 2013
	 */
	@Override
	public void handle(ScheduleEvent t) {
		
	}

}
