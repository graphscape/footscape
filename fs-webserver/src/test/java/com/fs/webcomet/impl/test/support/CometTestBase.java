/**
 * Jun 20, 2012
 */
package com.fs.webcomet.impl.test.support;

import java.net.URI;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.client.BClientFactoryI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.webcomet.api.WebCometComponents;
import com.fs.webcomet.impl.test.mock.MockCometBClient;
import com.fs.webcomet.impl.test.mock.MockCometServer;
import com.fs.webcomet.impl.test.mock.MockMessageWrapper;
import com.fs.webserver.impl.test.cases.support.TestBase;
import com.fs.websocket.api.Components;
import com.fs.websocket.impl.test.WebSocketTestSPI;

/**
 * @author wu
 * 
 */
public class CometTestBase extends TestBase {

	protected BClientFactoryI<MockCometBClient> clients;

	protected MockCometServer server;

	protected String protocol;

	protected long timeout = 5 * 1000;

	public CometTestBase(String protocol) {
		this.protocol = protocol;
	}

	/* */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		URI uri = null;
		String smanager = null;
		BClientFactoryI.ProtocolI cpro = null;
		if (this.protocol.equals("websocket")) {
			uri = WebSocketTestSPI.TEST_WS_URI;
			cpro = Components.WEBSOCKET;
			smanager = "testws";
		} else if (this.protocol.equals("ajax")) {
			uri = WebSocketTestSPI.TEST_AJAX_URI;
			smanager = "testajax";
			cpro = WebCometComponents.AJAX;
		} else {
			throw new FsException("no this protocol:" + this.protocol);
		}
		clients = BClientFactoryI.Builder.newInstance(MockCometBClient.class, this.container);
		clients.registerProtocol(cpro, uri);
		server = new MockCometServer(this.protocol, smanager, this.container);
		server.start();
	}

	protected void doTestPushMessageFromServer() {
		MockCometBClient ci = this.clients.createClient(this.protocol, false);
		ci.connect();
		String wsId = ci.getWsId(true);
		BlockingQueue<MessageI> queue = ci.getPushFromServerQueue();

		//
		MockMessageWrapper mm = MockMessageWrapper.valueOf("/push-from-server", "yes,pushed");
		this.server.sendMessage(wsId, mm);
		MessageI msg2 = null;
		try {
			msg2 = queue.poll(this.timeout, TimeUnit.MILLISECONDS);

		} catch (InterruptedException e) {
			throw new FsException(e);
		}
		assertNotNull("pushed message not received by client", msg2);
		
		assertEquals(Path.valueOf("/push-from-server"), msg2.getPath());

		//
		ci.close();

		this.server.waitClientClose();
	}

	protected void doTestClients() throws Exception {

		int CLS = 2;
		for (int i = 0; i < CLS; i++) {
			MockCometBClient ci = clients.createClient(this.protocol, false);

			ci.connect();//
			// sessionID
		}
		List<MockCometBClient> cl = clients.getClientList();
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
