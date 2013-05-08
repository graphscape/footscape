/**
 *  Dec 11, 2012
 */
package com.fs.webcomet.impl.test.support;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.webcomet.impl.test.mock.MockCometBClient;
import com.fs.webserver.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class MockCometTestBase extends TestBase {

	private static final Logger LOG = LoggerFactory.getLogger(MockCometTestBase.class);

	private static final boolean srmac = false;

	public void testClients() throws Exception {

		int CLS = 2;
		for (int i = 0; i < CLS; i++) {
			MockCometBClient ci = manager.createClient(false);
			
			ci.connect();//
			// sessionID
		}
		List<MockCometBClient> cl = manager.getClientList();
		for (int i = 0; i < CLS; i++) {
			MockCometBClient ci = cl.get(i);
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
