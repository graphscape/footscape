/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 25, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import com.fs.webserver.impl.test.mock.MockWSC;
import com.fs.websocket.api.mock.AbstractWSClientManager;
import com.fs.websocket.api.mock.WSClient;

/**
 * @author wu
 * 
 */
public class WSClientManagerImpl extends AbstractWSClientManager<WSClient> {

	/*
	 * Jan 25, 2013
	 */
	@Override
	protected WSClient newClient(int idx) {
		return new MockWSC("client-" + idx, uri);

	}

}
