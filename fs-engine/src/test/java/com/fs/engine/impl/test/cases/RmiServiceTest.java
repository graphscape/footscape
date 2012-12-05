/**
 * Jun 16, 2012
 */
package com.fs.engine.impl.test.cases;

import com.fs.commons.api.lang.FsException;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.RRContext;
import com.fs.engine.impl.EngineImpl;
import com.fs.engine.impl.test.cases.support.TestBase;
import com.fs.engine.impl.test.handler.TestHandler0;
import com.fs.engine.impl.test.handler.TestHandler1;
import com.fs.engine.impl.test.handler.TestHandler2;

/**
 * @author wu
 * 
 */
public class RmiServiceTest extends TestBase {

	public void testHandler1() {
		RequestI req = RRContext.newRequest();

		req.setPath("/handler1/hello");
		TestHandler1.ReqBean reqb = TestHandler1.newRequest();
		req.setPayload(reqb);// request

		ResponseI res = service.service(req);

		assertFalse("exception check failed.", res.getHeaders()
				.getPropertyAsBoolean("EXCEPTION", false));
		Object o = res.getPayload();// response
		assertNotNull(o);
		assertEquals(TestHandler1.ResBean.class, o.getClass());
		TestHandler1.ResBean b1 = TestHandler1.newResponse(reqb);

		assertEquals(b1, o);

	}

	public void testDefaultHandler() {
		RequestI req = RRContext.newRequest();
		ResponseI res = this.service.service(req);

		assertFalse("exception check failed.", res.getHeaders()
				.getPropertyAsBoolean("EXCEPTION", false));
		Object o = res.getPayload();

		assertEquals(TestHandler0.RESPONSE, o);

	}

	public void testHandler2Normal() {
		RequestI req = RRContext.newRequest();
		req.setPath("/handler2/hello");
		ResponseI res = service.service(req);

		assertFalse("exception check failed.", res.getHeaders()
				.getPropertyAsBoolean("EXCEPTION", false));
		Object o = res.getPayload();

		assertEquals(TestHandler2.MSG, o);

	}

	public void testHandler2ThrowException() {
		RequestI req = RRContext.newRequest();
		req.setPath("/handler2/hello");
		req.getHeaders().setProperty(TestHandler2.HD_ISTHROW, "true");
		ResponseI res = service.service(req);
		boolean exp = res.getHeaders().getPropertyAsBoolean("EXCEPTION", false);
		assertTrue("exception check failed.", exp);
		Object o = res.getPayloads().getProperty("EXCEPTION");
		assertNotNull(
				"exception payload missing,see:" + EngineImpl.class.getName(),
				o);
		assertTrue(FsException.class.isInstance(o));
		FsException fse = FsException.class.cast(o);
		String msg = fse.getMessage();
		assertEquals(TestHandler2.THROW_MSG, msg);

	}

}
