/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.test.cases;

import com.fs.commons.api.client.BClientFactoryI.ProtocolI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.expector.gridservice.api.Constants;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ResourceHandlerTest extends TestBase {

	/**
	 * @param p
	 */
	public ResourceHandlerTest(ProtocolI p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	private MockExpectorClient client1;

	private MockExpectorClient client2;

	public void testExpAndConnect() {
		this.client1 = this.newClient("user1@domain1.com", "user1");
		{
			MessageI req = new MessageSupport("resource/get");
			req.setHeader("url", "classpath:///com/fs/resource/test-resource.html");

			MessageI res = this.client1.syncSendMessage(req);
			ErrorInfos eis = res.getErrorInfos();
			assertTrue("error expected", eis.hasError());
			assertEquals(1, eis.getErrorInfoList().size());
			ErrorInfo ei = eis.getErrorInfoList().get(0);
			assertEquals(Constants.P_ERROR_NOTALLOW.toString(), ei.getCode());

		}
		{
			MessageI req = new MessageSupport("resource/get");
			req.setHeader("url", "classpath:///user/open/resource/test-resource.html");

			MessageI res = this.client1.syncSendMessage(req);

			ErrorInfos eis = res.getErrorInfos();
			assertFalse("error:" + eis, eis.hasError());

			String rst = (String) res.getPayload("resource");
			assertEquals("<div>hello,this is a test</div>", rst);

			System.out.println(rst);
		}

	}

}
