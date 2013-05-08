/**
 *  Dec 11, 2012
 */
package com.fs.websocket.impl.test.cases;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.struct.Path;
import com.fs.webserver.impl.test.cases.support.TestBase;
import com.fs.webserver.impl.test.mock.MockMessageWrapper;
import com.fs.webserver.impl.test.mock.MockWsBClient;

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
			MockWsBClient ci = manager.createClient(false);
			
			ci.connect();//
			// sessionID
		}
		List<MockWsBClient> cl = manager.getClientList();
		for (int i = 0; i < CLS; i++) {
			MockWsBClient ci = cl.get(i);
			int idx = (i + 1 == CLS) ? 0 : (i + 1);
			String to = cl.get(idx).getWsId(true);

			String text = "hello " + to;
			String from = ci.getWsId(true);
			String echo = ci.echo(text);
			assertNotNull("message not got", echo);
			assertEquals(text, echo);
		}

		// close all client.
		for (int i = 0; i < CLS; i++) {
			cl.get(i).close();
		}

		//
		server.waitClientClose();//
	}
}
