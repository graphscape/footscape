/**
 * Jun 20, 2012
 */
package com.fs.webserver.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.client.BClientManagerI;
import com.fs.webserver.impl.test.mock.MockWsBClient;
import com.fs.webserver.impl.test.mock.ssocket.MockWsServer;
import com.fs.websocket.impl.mock.MockWSClientImpl;
import com.fs.websocket.impl.test.WebSocketTestSPI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected static SPIManagerI sm;
	protected ContainerI container;

	protected BClientManagerI<MockWsBClient> manager;

	protected MockWsServer server;

	/* */
	@Override
	protected void setUp() throws Exception {
		if (sm != null) {
			return;
		}

		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		manager = BClientManagerI.Factory.newInstance(MockWSClientImpl.class, WebSocketTestSPI.TEST_WS_URI,
				MockWsBClient.class, this.container);
		server = new MockWsServer("testws", this.container);
		server.start();
	}

	@Override
	protected void tearDown() {

	}

	/* */
	@Override
	public void setName(String name) {

		super.setName(name);

	}

}
