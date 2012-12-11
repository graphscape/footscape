/**
 *  Dec 11, 2012
 */
package com.fs.webserver.impl.test.cases;

import java.net.URI;

import org.eclipse.jetty.util.FutureCallback;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.webserver.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class WebSocketTest extends TestBase {

	private static final Logger LOG = LoggerFactory
			.getLogger(WebSocketTest.class);

	public void testClient() throws Exception {
		WebSocketClientFactory cf = new WebSocketClientFactory();
		WebSocketListener wsl = new WebSocketAdapter() {

			@Override
			public void onWebSocketText(String message) {
				LOG.info("msg:" + message);
			}

		};
		WebSocketClient wsc = cf.newWebSocketClient(wsl);
		//
		URI uri = new URI("ws://localhost");
		FutureCallback<UpgradeResponse> fc = wsc.connect(uri);//
		UpgradeResponse res = fc.get();
		res.isSuccess();

	}
}
