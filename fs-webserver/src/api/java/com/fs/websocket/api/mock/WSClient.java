/**
 *  Dec 28, 2012
 */
package com.fs.websocket.api.mock;

import java.net.URI;

import com.fs.commons.api.lang.ClassUtil;

/**
 * @author wuzhen TODO move to web socket
 */
public abstract class WSClient {

	public abstract WSClient connect();

	public abstract void sendMessage(String text);

	public abstract void close();

	public static WSClient newInstance(String name, URI uri) {
		WSClient rt = (WSClient) ClassUtil.newInstance("com.fs.websocket.impl.mock.MockWSClientImpl",
				new Class[] { String.class, URI.class }, new Object[] { name, uri });

		return rt;
	}

}
