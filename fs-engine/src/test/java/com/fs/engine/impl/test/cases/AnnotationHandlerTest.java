/**
 * Jun 22, 2012
 */
package com.fs.engine.impl.test.cases;

import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.RRContext;
import com.fs.engine.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class AnnotationHandlerTest extends TestBase {

	public void test() {
		{
			String path = "/handler3/path1";
			RequestI req = RRContext.newRequest();
			req.setPath(path);
			ResponseI res = this.service.service(req);
			String pl = (String) res.getPayload();
			assertEquals(path, pl);
		}
		{
			String path = "/handler3/path2";
			RequestI req = RRContext.newRequest();
			req.setPath(path);
			ResponseI res = this.service.service(req);
			String pl = (String) res.getPayload();
			assertEquals(path, pl);
		}

	}
}
