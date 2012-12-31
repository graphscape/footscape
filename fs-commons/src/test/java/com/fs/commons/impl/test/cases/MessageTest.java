/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.test.cases;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.HandlerSupport;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.struct.Path;
import com.fs.commons.impl.test.TestSPI;
import com.fs.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class MessageTest extends TestBase {
	public static class AnnotationTestHandler extends HandlerSupport {

		public AnnotationTestHandler() {

		}

		public AnnotationTestHandler(String name) {
			super(name);
		}

		@Override
		public void handle(MessageContext hc) {
			super.handle(hc);
		}

		@Handle("path1")
		public void handlePathOne(MessageI req, ResponseI res, MessageContext hc) {
			res.setPayload("handler", this.getName());//
			res.setPayload("method", "path1");
		}

		public void handlePath2(MessageI req, ResponseI res) {
			res.setPayload("handler", this.getName());//
			res.setPayload("method", "path2");
		}

	}

	public void testMessageService() {
		MessageServiceI.FactoryI sf = this.container.find(
				MessageServiceI.FactoryI.class, true);
		MessageServiceI ms = sf.create("test-ms");
		DispatcherI<MessageContext> ds = ms.getDispatcher();
		Path pAB = Path.valueOf("/a/b");
		Path pEF = Path.valueOf("/e/f");

		ds.addHandler(null,pAB, new AnnotationTestHandler(pAB.toString()));
		ds.addHandler(TestSPI.class.getName() + ".messageTest./e/f");
		Path p1 = Path.valueOf(pAB, "path1");
		Path p2 = Path.valueOf(pAB, "path2");
		
		Path p3 = Path.valueOf(pEF,"path1");

		{
			MessageI req = new MessageSupport(p1.toString());
			ResponseI res = ms.service(req);
			res.assertNoError();
			assertEquals(pAB.toString(), res.getPayload("handler"));
			assertEquals("path1", res.getPayload("method"));

		}
		{
			MessageI req = new MessageSupport(p2.toString());
			ResponseI res = ms.service(req);
			res.assertNoError();
			assertEquals(pAB.toString(), res.getPayload("handler"));
			assertEquals("path2", res.getPayload("method"));

		}
		{
			MessageI req = new MessageSupport(p3.toString());
			ResponseI res = ms.service(req);
			res.assertNoError();
			assertEquals(pEF.toString(), res.getPayload("handler"));
			assertEquals("path1", res.getPayload("method"));

		}
	}
}
