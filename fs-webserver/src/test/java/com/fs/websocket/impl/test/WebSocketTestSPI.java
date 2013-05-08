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
import com.fs.webcomet.api.CometFactoryI;
import com.fs.webcomet.api.CometManagerI;

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

		CometFactoryI f = ac.getContainer().find(CometFactoryI.class, true);
		CometManagerI mnr = f.addManager(ac, "websocket", "testws");

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
