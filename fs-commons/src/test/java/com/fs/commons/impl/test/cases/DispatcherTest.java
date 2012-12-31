/**
 * Jul 22, 2012
 */
package com.fs.commons.impl.test.cases;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.service.ServiceContext;
import com.fs.commons.api.struct.Path;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class DispatcherTest extends TestBase {

	public static class MyHandler implements
			HandlerI<ServiceContext<MessageI, MessageI>> {
		private String name;

		public MyHandler(String name) {
			this.name = name;
		}

		@Override
		public void handle(ServiceContext<MessageI, MessageI> sc) {
			sc.getResponse().setPayload("handler", this.name);
		}

	}

	public void testDispatcher() {
		DispatcherI.FactoryI df = this.container.find(
				DispatcherI.FactoryI.class, true);
		DispatcherI<ServiceContext<MessageI, MessageI>> d = df.create("test");

		Path p1 = Path.valueOf("/a/b", '/');
		Path p11 = Path.valueOf(p1, "c");

		Path p2 = Path.valueOf("/e/f", '/');
		Path p21 = Path.valueOf(p2, "g");

		Path p3 = Path.valueOf("/x/y", '/');
		Path p31 = Path.valueOf(p3, "z");

		d.addHandler(null, p1, new MyHandler(p1.toString()));
		d.addHandler(null, p2, new MyHandler(p2.toString()));
		d.addHandler(null, p3, true, new MyHandler(p2.toString()));

		MessageI req = null;
		ServiceContext<MessageI, MessageI> sc = null;
		{
			req = new MessageSupport(p11.toString());
			sc = new ServiceContext<MessageI, MessageI>(req,
					new MessageSupport());
			d.dispatch(p11, sc);
			assertEquals(p1.toString(), sc.getResponse().getPayload("handler"));
		}
		{
			req = new MessageSupport(p21.toString());
			sc = new ServiceContext<MessageI, MessageI>(req,
					new MessageSupport());
			d.dispatch(p21, sc);
			assertEquals(p2.toString(), sc.getResponse().getPayload("handler"));
		}
		{
			req = new MessageSupport(p31.toString());
			sc = new ServiceContext<MessageI, MessageI>(req,
					new MessageSupport());
			d.dispatch(p31, sc);
			assertEquals(null, sc.getResponse().getPayload("handler"));
		}

	}
}
