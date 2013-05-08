/**
 *  Jan 29, 2013
 */
package com.fs.websocket.impl.test.cases;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.webcomet.impl.test.mock.MockCometBClient;
import com.fs.webserver.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class KeepLiveTest extends TestBase {

	public void testKeepLive() {
		MockCometBClient client = this.manager.createClient(true);
		String text = "test-keep-live-0";
		MessageI msg = new MessageSupport("echo");
		msg.setPayload("text", text);
		client.sendMessage(msg);
		try {
			Thread.sleep(60 * 1000);//
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

		MessageI res = client.syncSendMessage(msg);
		String text2 = res.getString("text", true);
		assertEquals(text, text2);
	}
}
