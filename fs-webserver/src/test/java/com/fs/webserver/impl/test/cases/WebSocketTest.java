/**
 *  Dec 11, 2012
 */
package com.fs.webserver.impl.test.cases;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.webserver.impl.test.cases.support.TestBase;
import com.fs.webserver.impl.test.mock.MockMessage;
import com.fs.webserver.impl.test.mock.MockWSC;
import com.fs.webserver.impl.test.mock.MockWSCFactory;
import com.fs.webserver.impl.test.mock.ssocket.MockWsServer;

/**
 * @author wuzhen
 * 
 */
public class WebSocketTest extends TestBase {

	private static final Logger LOG = LoggerFactory
			.getLogger(WebSocketTest.class);

	public void testClients() throws Exception {
		MockWsServer mserver = this.prepareServer();
		
		
		MockWSCFactory cf = new MockWSCFactory();
		cf.start();

		URI uri = new URI("ws://localhost:8080/wsa/testws");
		int CLS = 2;
		MockWSC[] clients = new MockWSC[CLS];
		for (int i = 0; i < CLS; i++) {
			clients[i] = cf.newClient("client-" + i, uri);
			Future<MockWSC> fc = clients[i].connect();//
			
			MockWSC c = fc.get(1000, TimeUnit.MILLISECONDS);
			
		}
		for (int i = 0; i < CLS; i++) {
			String to = clients[(i + 1 == CLS) ? 0 : (i + 1)].getId();
			String text = "hello " + to;
			clients[i].sendMessage(to, text);

		}
		for (int i = 0; i < CLS; i++) {
			MockMessage mm = clients[i].nextMessage(1000);
			LOG.info("msg:" + mm);
		}
		//

	}

	private MockWsServer prepareServer() {
		MockWsServer rt = new MockWsServer("testws",this.container); 
		rt.start();
		return rt;
	}
}
