/**
 * Jun 20, 2012
 */
package com.fs.webcomet.impl.test.support;

import java.net.URI;
import java.util.List;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.client.BClientManagerI;
import com.fs.commons.api.lang.FsException;
import com.fs.webcomet.impl.mock.MockAjaxClientImpl;
import com.fs.webcomet.impl.test.mock.MockCometBClient;
import com.fs.webcomet.impl.test.mock.MockCometServer;
import com.fs.webserver.impl.test.cases.support.TestBase;
import com.fs.websocket.impl.mock.MockWSClientImpl;
import com.fs.websocket.impl.test.WebSocketTestSPI;

/**
 * @author wu
 * 
 */
public class CometTestBase extends TestBase {

	protected BClientManagerI<MockCometBClient> manager;

	protected MockCometServer server;

	protected String protocol;

	public CometTestBase(String protocol) {
		this.protocol = protocol;
	}

	/* */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		URI uri = null;
		String smanager = null;
		Class<? extends AClientI> acls = null;
		if (this.protocol.equals("websocket")) {
			uri = WebSocketTestSPI.TEST_WS_URI;
			acls = MockWSClientImpl.class;
			smanager = "testws";
		} else if (this.protocol.equals("ajax")) {
			uri = WebSocketTestSPI.TEST_AJAX_URI;
			acls = MockAjaxClientImpl.class;
			smanager = "testajax";
		} else {
			throw new FsException("no this protocol:" + this.protocol);
		}
		manager = BClientManagerI.Factory.newInstance(acls, uri, MockCometBClient.class, this.container);

		server = new MockCometServer(smanager, this.container);
		server.start();
	}

	protected void doTestClients() throws Exception {

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
