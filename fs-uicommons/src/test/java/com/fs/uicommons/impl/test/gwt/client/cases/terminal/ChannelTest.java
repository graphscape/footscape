/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.terminal;

import com.fs.uicommons.api.gwt.client.channel.ChannelI;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelCloseEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelErrorEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelMessageEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelOpenEvent;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
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
		ChannelI ti = this.client.getChild(ChannelI.class, true);
		ti.addHandler(ChannelEvent.TYPE, new HandlerI<ChannelEvent>() {

			@Override
			public void handle(ChannelEvent e) {
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

	public void onChannelEvent(ChannelEvent e) {
		if (e instanceof ChannelOpenEvent) {
			this.onOpen((ChannelOpenEvent) e);
		} else if (e instanceof ChannelCloseEvent) {
			this.onClose((ChannelCloseEvent) e);
		} else if (e instanceof ChannelMessageEvent) {

			this.onMessage((ChannelMessageEvent) e);

		} else if (e instanceof ChannelErrorEvent) {
			this.onError((ChannelErrorEvent) e);
		}
	}

	/**
	 * Dec 22, 2012
	 */
	private void onError(ChannelErrorEvent e) {
		//
		System.out.println("channelerror:" + e);
	}

	public void onOpen(ChannelOpenEvent ce) {
		ChannelI c = ce.getChannel();
		System.out.println("channel opened:" + c.getUri());
		opens++;
		this.tryFinish("open-" + opens);

	}

	/**
	 * Dec 22, 2012
	 */
	private void onMessage(ChannelMessageEvent e) {
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
	private void onClose(ChannelCloseEvent e) {
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
