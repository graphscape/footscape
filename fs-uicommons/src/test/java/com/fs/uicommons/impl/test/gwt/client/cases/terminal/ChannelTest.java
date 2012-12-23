/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.terminal;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointCloseEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointErrorEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointMessageEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointOpenEvent;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.BeforeClientStartEvent;

/**
 * @author wuzhen
 * 
 */
public class ChannelTest extends TestBase {

	private int opens;

	private int closes;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

	}

	@Override
	protected void beforeClientStart(BeforeClientStartEvent e) {
		EndPointI ti = this.client.getChild(EndPointI.class, true);
		ti.addHandler(EndpointEvent.TYPE, new EventHandlerI<EndpointEvent>() {

			@Override
			public void handle(EndpointEvent e) {
				ChannelTest.this.onChannelEvent(e);
			}
		});
	}

	@Override
	protected void onClientStart(AfterClientStartEvent e) {
		String sid = this.client.getSessionId();

	}

	public void testTerminal() {

		this.finishing.add("open-1");
		this.finishing.add("serverIsReady");
		this.finishing.add("ping/success");
		

		this.delayTestFinish(this.timeoutMillis * 10);

	}

	public void onChannelEvent(EndpointEvent e) {
		if (e instanceof EndpointOpenEvent) {
			this.onOpen((EndpointOpenEvent) e);
		} else if (e instanceof EndpointCloseEvent) {
			this.onClose((EndpointCloseEvent) e);
		} else if (e instanceof EndpointMessageEvent) {

			this.onMessage((EndpointMessageEvent) e);

		} else if (e instanceof EndpointErrorEvent) {
			this.onError((EndpointErrorEvent) e);
		}
	}

	/**
	 * Dec 22, 2012
	 */
	private void onError(EndpointErrorEvent e) {
		//
		System.out.println("channelerror:" + e);
	}

	public void onOpen(EndpointOpenEvent ce) {
		EndPointI c = ce.getChannel();
		System.out.println("channel opened:" + c.getUri());
		opens++;
		this.tryFinish("open-" + opens);

	}

	/**
	 * Dec 22, 2012
	 */
	private void onMessage(EndpointMessageEvent e) {
		//
		MessageData md = e.getMessage();
		System.out.println("channel msg:" + md);
		String path = md.getHeader("path");
		if (path.endsWith("serverIsReady")) {
			// send hello to server,server will echo
			MessageData req = new MessageData();
			req.setHeader("path", "/ping/ping");
			req.setPayload("text", StringData.valueOf("hello"));
			e.getChannel().sendMessage(req);
			this.tryFinish("serverIsReady");
		} 
		
		if (path.endsWith("ping/success")) {
			StringData textD = (StringData) md.getPayload("text", true);
			System.out.println(textD);
			this.tryFinish("ping/success");
		}
		this.tryFinish(path);//

	}

	/**
	 * Dec 22, 2012
	 */
	private void onClose(EndpointCloseEvent e) {
		//
		System.out.println("channel close,e:" + e);
	
		closes++;

		this.tryFinish("close-" + closes);//
		if (closes == 1) {
			MessageData req = new MessageData();
			req.setHeader("path", "/ping/ping");
			req.setPayload("text", StringData.valueOf("hello2"));
			
			e.getChannel().sendMessage(req);
		}

	}

}
