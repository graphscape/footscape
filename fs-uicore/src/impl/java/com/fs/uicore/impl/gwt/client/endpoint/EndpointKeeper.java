/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 11, 2013
 */
package com.fs.uicore.impl.gwt.client.endpoint;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
import com.fs.uicore.api.gwt.client.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.scheduler.SchedulerI;

/**
 * @author wu
 * 
 */
public class EndpointKeeper {

	protected static final int PING_INTERVAL = 30 * 1000;

	protected EndPointI endpoint;

	public EndpointKeeper(EndPointI ep) {
		this.endpoint = ep;
	}

	public void start() {
		this.endpoint.addHandler(EndpointMessageEvent.TYPE, new EventHandlerI<EndpointMessageEvent>() {

			@Override
			public void handle(EndpointMessageEvent t) {
				EndpointKeeper.this.onMessage(t);
			}
		});
		this.endpoint.addHandler(EndpointOpenEvent.TYPE, new EventHandlerI<EndpointOpenEvent>() {

			@Override
			public void handle(EndpointOpenEvent t) {
				EndpointKeeper.this.onOpen(t);
			}
		});
	}

	/**
	 * Jan 11, 2013
	 */
	protected void onOpen(EndpointOpenEvent t) {
		SchedulerI s = this.endpoint.getContainer().get(SchedulerI.class, true);
		s.scheduleRepeat("endpoint-keeper", PING_INTERVAL, new EventHandlerI<ScheduleEvent>() {

			@Override
			public void handle(ScheduleEvent t) {
				EndpointKeeper.this.onScheduleEvent(t);
			}
		});// 30S to send ping

	}

	/**
	 * Jan 11, 2013
	 */
	protected void onScheduleEvent(ScheduleEvent t) {
		MsgWrapper req = new MsgWrapper("/terminal/ping");
		this.endpoint.sendMessage(req);
	}

	/**
	 * Jan 11, 2013
	 */
	protected void onMessage(EndpointMessageEvent t) {
		// TODO /ping/success

	}

}
