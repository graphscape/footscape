/**
 *  Dec 28, 2012
 */
package com.fs.gridservice.commons.impl.test.cases;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.QueueMessageHandler;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class ResponseTest extends TestBase {

	public void testSession() throws Exception {

		// assert websocket is refed.
		MockClient client = this.newClientAndAuth("acc1");
		MessageI msg = new MessageSupport();
		String tid = client.getTerminalId();
		msg.setHeader(MessageI.HK_RESPONSE_ADDRESS, "tid://" + tid);
		msg.setHeader(MessageI.HK_PATH, "/not-exist");
		QueueMessageHandler mh = new QueueMessageHandler();
		client.getDispatcher().addHandler(null, Path.valueOf(new String[] {}),
				mh);
		client.sendMessage(msg);
		MessageI res = mh.take().getRequest();//
		System.out.println(res);

	}

}
