/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 11, 2013
 */
package com.fs.uicommons.impl.gwt.client;

import com.fs.uicommons.api.gwt.client.UiCommonsConstants;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.EndpointCloseEvent;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.fs.uicore.api.gwt.client.scheduler.SchedulerI;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class EndpointKeeper {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(EndpointKeeper.class);

	protected EndPointI endpoint;

	private UiClientI client;

	private String taskName = "endpoint-keeper";

	private DisconnectedWidgetImpl disconnected;

	public EndpointKeeper(UiClientI c) {
		this.endpoint = c.getEndpoint();
		this.client = c;

		RootI root = this.client.getRoot();
		disconnected = new DisconnectedWidgetImpl(this.client.getContainer());
		disconnected.setVisible(false);
		disconnected.parent(root);
	}

	public void start() {
		this.endpoint.addHandler(EndpointMessageEvent.TYPE, new EventHandlerI<EndpointMessageEvent>() {

			@Override
			public void handle(EndpointMessageEvent t) {
				EndpointKeeper.this.onMessage(t);
			}
		});

		this.endpoint.addHandler(EndpointCloseEvent.TYPE, new EventHandlerI<EndpointCloseEvent>() {

			@Override
			public void handle(EndpointCloseEvent t) {
				EndpointKeeper.this.onClose(t);
			}
		});

		int hbI = this.client.getParameterAsInt(UiCommonsConstants.RK_WS_HEARTBEATINTERVAL, -1);
		
		if (hbI > 5 * 1000) {// must longer than 5 second
			
			SchedulerI s = this.endpoint.getContainer().get(SchedulerI.class, true);

			s.scheduleRepeat(taskName, hbI, new EventHandlerI<ScheduleEvent>() {

				@Override
				public void handle(ScheduleEvent t) {
					EndpointKeeper.this.onScheduleEvent(t);
				}
			});// 30S to send ping
		}
	}

	/**
	 * Jan 12, 2013
	 */
	protected void onClose(EndpointCloseEvent t) {
		LOG.info("endpoint close:" + t);
		this.disconnected.setVisible(true);
		boolean rec = Window.confirm("Connection to server is lost, re-connect to server?");

		if (rec) {
			Window.Location.reload();
		}

	}

	/**
	 * Jan 11, 2013
	 */
	protected void onScheduleEvent(ScheduleEvent t) {
		MsgWrapper req = new MsgWrapper("/ping/ping");
		req.setPayload("text", "keeper");
		this.endpoint.sendMessage(req);
	}

	/**
	 * Jan 11, 2013
	 */
	protected void onMessage(EndpointMessageEvent t) {
		// TODO /ping/success

	}

}