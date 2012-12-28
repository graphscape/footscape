/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 7, 2012
 */
package com.fs.engine.impl.test.cases;

import java.util.List;

import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.RRContext;
import com.fs.engine.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ErrorResponseTest extends TestBase {

	public void testHandlerNotFound() {

		String path = "/handler5/throw";

		RequestI req = RRContext.newRequest(path);
		ResponseI res = this.service.service(req);
		String pl = (String) res.getPayload();
		ErrorInfos eis = res.getErrorInfos();
		assertTrue("should report exception in handler", eis.hasError());
		List<ErrorInfo> eL = eis.getErrorInfoList();
		assertEquals("should only one error info,but:" + eL, 1, eL.size());
		ErrorInfo ei = eL.get(0);
		System.out.println(ei);

	}
}
