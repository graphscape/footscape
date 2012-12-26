/**
 *  Dec 24, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.message;

import com.fs.uicommons.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicommons.api.gwt.client.message.MessageHandlerI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

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
		public void handle(MessageData t) {
			test.onMessage(this, t);
		}
	}

	public DispatcherTest() {
		super.disableWebSocket = true;//
	}

	public void testDispatcher() {

		MessageDispatcherI.FactoryI df = this.client.find(
				MessageDispatcherI.FactoryI.class, true);
		final MessageDispatcherI d0 = df.get("test0");
		final MessageDispatcherI d1 = df.get("test1");

		final Path p0 = Path.valueOf("p0", "p0-1");
		final Path p1 = Path.valueOf("p1", "p1-1");

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
			d0.handle(m1);
		}
		{
			MessageData m1 = new MessageData("/p0/p0-1/p0-2");
			m1.setHeader("handler", "handler0");
			m1.setHeader("finishing", "02");
			d0.handle(m1);
		}
		{
			MessageData m1 = new MessageData("/p1/p1-1");
			m1.setHeader("handler", "handler1");
			m1.setHeader("finishing", "11");
			d0.handle(m1);
		}
		{
			MessageData m1 = new MessageData("/p1/p1-1/p1-2");
			m1.setHeader("handler", "handler1");
			m1.setHeader("finishing", "12");
			d0.handle(m1);
		}
	}

	/**
	 * @param p1p2
	 * @param t
	 */
	protected void onMessage(MyHandler mh, MessageData t) {
		//
		String hname = mh.name;
		String hname2 = t.getHeader("handler", true);
		assertEquals("handler wrong for message:" + t, hname, hname2);

		String finishing = t.getHeader("finishing", true);

		this.tryFinish(finishing);
	}
}
