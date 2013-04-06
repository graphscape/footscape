/**
 * Jul 10, 2012
 */
package com.fs.websocket.impl.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.SPISupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.api.WsListenerI;
import com.fs.websocket.api.WsManagerI;

/**
 * @author wu
 * 
 */
public class WebSocketTestSPI extends SPISupport {

	private static Logger LOG = Log.getLog();//
	public static final URI TEST_WS_URI;
	static {
		try {
			TEST_WS_URI = new URI("ws://localhost:8080/wsa/testws");
		} catch (URISyntaxException e) {
			throw new FsException(e);
		}
	}

	/** */
	public WebSocketTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {

		WsFactoryI f = ac.getContainer().find(WsFactoryI.class, true);
		WsManagerI mnr = f.addManager(ac, "testws");

	}

	/*
	 *Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		// 
		
	}

}
