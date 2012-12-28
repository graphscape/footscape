/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.impl.mock;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.websocket.client.WebSocketClientFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.FsException;
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.api.mock.MockClientFactory;

/**
 * @author wu
 * 
 */
public class MockClientFactoryImpl extends MockClientFactory {

	protected WebSocketClientFactory cf = new WebSocketClientFactory();

	protected ContainerI container;

	public MockClientFactoryImpl start(ContainerI c) {
		this.container = c;
		try {
			cf.start();
		} catch (Exception e) {
			throw FsException.toRtE(e);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.mock.MockClientFactory#newClient(java.
	 * lang.String)
	 */
	@Override
	public MockClient newClient(String url) {
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			throw new FsException(e);
		}
		MockClientBase rt = new MockClientImpl(this.cf, this.container, uri);
		return rt;
	}

}
