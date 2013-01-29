/**
 * Jun 20, 2012
 */
package com.fs.webserver.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.webserver.impl.test.mock.MockWSClientWrapper;
import com.fs.webserver.impl.test.mock.ssocket.MockWsServer;
import com.fs.websocket.api.mock.WSClientManager;
import com.fs.websocket.impl.test.WebSocketTestSPI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected SPIManagerI sm;
	protected ContainerI container;

	protected WSClientManager<MockWSClientWrapper> manager;

	protected MockWsServer server;
	/* */
	@Override
	protected void setUp() throws Exception {
		if (this.sm != null) {
			return;
		}
		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		manager = WSClientManager.newInstance(WebSocketTestSPI.TEST_WS_URI, MockWSClientWrapper.class,
				this.container);
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
