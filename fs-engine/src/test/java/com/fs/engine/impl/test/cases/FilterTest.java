/**
 * Jun 19, 2012
 */
package com.fs.engine.impl.test.cases;

import org.junit.Test;

import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.RRContext;
import com.fs.engine.impl.test.cases.support.TestBase;
import com.fs.engine.impl.test.filter.Filter2;

/**
 * @author wu
 * 
 */
public class FilterTest extends TestBase {

	@Test
	public void test() {
		RequestI req = RRContext.newRequest();
		req.setPayload(null);// request

		ResponseI res = this.service.service(req);
		Object o = res.getPayload();// response
		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		String value = res.getHeaders().getProperty(Filter2.KEY);
		assertEquals(Filter2.VALUE, value);
	}

}
