/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.terminal;

import com.fs.uicommons.api.gwt.client.channel.ChannelI;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelCloseEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelMessageEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelOpenEvent;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;

/**
 * @author wuzhen
 * 
 */
public class ChannelTest extends TestBase {

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		this.finishing.add("waitopen");
		// this.finishing.add("waitmessage");
		this.finishing.add("waitclose");

	}

	public void testTerminal() {

		ChannelI ti = this.client.getChild(ChannelI.class, true);
		ti.addHandler(ChannelEvent.TYPE, new HandlerI<ChannelEvent>() {

			@Override
			public void handle(ChannelEvent e) {
				ChannelTest.this.onChannelEvent(e);
			}
		});
		this.delayTestFinish(this.timeoutMillis * 10);
		if (ti.isOpen()) {
			this.onOpen(ti);
		}

	}

	public void onOpen(ChannelI c) {

		System.out.println("channel opened:" + c.getUri());

		this.tryFinish("waitopen");

	}

	public void onChannelEvent(ChannelEvent e) {
		if (e instanceof ChannelOpenEvent) {
			this.tryFinish("waitopen");
		} else if (e instanceof ChannelCloseEvent) {
			this.tryFinish("waitclose");
		} else if (e instanceof ChannelMessageEvent) {
			this.tryFinish("waitmsg");
		}
	}

}
