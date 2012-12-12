/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock;

import java.net.URI;

import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.WebSocketClientFactory;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public class MockWSCFactory {
	protected WebSocketClientFactory factory;

	public MockWSCFactory() {
		this.factory = new WebSocketClientFactory();
	}

	public MockWSC newClient(String id, URI uri) {
		MockWSC rt = new MockWSC(id, uri);
		WebSocketClient wsc = factory.newWebSocketClient(rt);
		rt.init(wsc);
		return rt;
	}

	/**
	 * 
	 */
	public void start() {
		try {
			this.factory.start();
		} catch (Exception e) {
			throw FsException.toRtE(e);
		}
	}
}
