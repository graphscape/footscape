/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.impl.test.mock;

import java.net.URI;

import org.eclipse.jetty.websocket.client.WebSocketClientFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class MockClientFactory {

	protected WebSocketClientFactory cf = new WebSocketClientFactory();

	public MockClient newClient(String sid, ContainerI c, URI uri) {
		return new MockClient(cf, sid, c, uri);
	}

	public MockClientFactory start() {
		try {
			cf.start();
		} catch (Exception e) {
			throw FsException.toRtE(e);
		}
		return this;
	}

}
