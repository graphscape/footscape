/**
 *  Dec 24, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class DispatcherTest extends TestBase {

	private static class MyHandler implements MessageHandlerI {
		private DispatcherTest test;
		private Path path;
		private String name;

		public MyHandler(String name, DispatcherTest test, Path path) {
			this.name = name;
			this.test = test;
			this.path = path;

		}

		@Override
		public void handle(EndpointMessageEvent t) {
			test.onMessage(this, t);
		}
	}

	public void testDispatcher() {

		MessageDispatcherI.FactoryI df = this.client.find(MessageDispatcherI.FactoryI.class, true);
		final MessageDispatcherI d0 = df.get("test0");
		final MessageDispatcherI d1 = df.get("test1");

		final Path p0 = Path.valueOf("p0/p0-1");
		final Path p1 = Path.valueOf("p1/p1-1");

		d0.addHandler(p0, new MyHandler("handler0", this, p0));
		d0.addHandler(p1, d1);//
		d1.addHandler(p1, new MyHandler("handler1", this, p1));
		this.finishing.add("01");
		this.finishing.add("02");
		this.finishing.add("11");
		this.finishing.add("12");

		this.delayTestFinish(100000);
		{
			MessageData m1 = new MessageData("/p0/p0-1");
			m1.setHeader("handler", "handler0");
			m1.setHeader("finishing", "01");
			EndpointMessageEvent evt = new EndpointMessageEvent(null, m1);
			d0.handle(evt);
		}
		{
			MessageData m1 = new MessageData("/p0/p0-1/p0-2");
			m1.setHeader("handler", "handler0");
			m1.setHeader("finishing", "02");
			EndpointMessageEvent evt = new EndpointMessageEvent(null, m1);
			d0.handle(evt);
		}
		{
			MessageData m1 = new MessageData("/p1/p1-1");
			m1.setHeader("handler", "handler1");
			m1.setHeader("finishing", "11");
			EndpointMessageEvent evt = new EndpointMessageEvent(null, m1);
			d0.handle(evt);
		}
		{
			MessageData m1 = new MessageData("/p1/p1-1/p1-2");
			m1.setHeader("handler", "handler1");
			m1.setHeader("finishing", "12");
			EndpointMessageEvent evt = new EndpointMessageEvent(null, m1);
			d0.handle(evt);
		}
	}

	/**
	 * @param p1p2
	 * @param t
	 */
	protected void onMessage(MyHandler mh, EndpointMessageEvent t) {
		//
		String hname = mh.name;
		String hname2 = t.getMessage().getHeader("handler", true);
		assertEquals("handler wrong for message:" + t, hname, hname2);

		String finishing = t.getMessage().getHeader("finishing", true);

		this.tryFinish(finishing);
	}
}
