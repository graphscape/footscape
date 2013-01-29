/**
 *  Dec 11, 2012
 */
package com.fs.websocket.impl.test.cases;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.webserver.impl.test.cases.support.TestBase;
import com.fs.webserver.impl.test.mock.MockMessageWrapper;
import com.fs.webserver.impl.test.mock.MockWSClientWrapper;

/**
 * @author wuzhen
 * 
 */
public class WebSocketTest extends TestBase {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketTest.class);

	private static final boolean srmac = false;

	public void testClients() throws Exception {

		int CLS = 2;
		for (int i = 0; i < CLS; i++) {
			MockWSClientWrapper ci = manager.createClient(false);
			ci.connect();//
			// sessionID
		}
		List<MockWSClientWrapper> cl = manager.getClientList();
		for (int i = 0; i < CLS; i++) {
			MockWSClientWrapper ci = cl.get(i);
			int idx = (i + 1 == CLS) ? 0 : (i + 1);
			String to = cl.get(idx).getWsId(true);

			String text = "hello " + to;
			String from = ci.getWsId(true);
			// TODO send message

		}
		// send from one to another,
		// each client will receive a message;
		for (int i = 0; i < CLS; i++) {
			MockMessageWrapper mm = null;// cl.get(i).tryAcquireNextMessage(10*1000);
			assertNotNull("message not got", mm);
			LOG.info("msg:" + mm);
		}

		// close all client.
		for (int i = 0; i < CLS; i++) {
			cl.get(i).close();
		}

		//
		server.waitClientClose();//
	}
}
