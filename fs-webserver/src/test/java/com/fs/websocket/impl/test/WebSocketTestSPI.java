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
	public static final URI TEST_AJAX_URI;
	static {
		try {
			TEST_WS_URI = new URI("ws://localhost:8080/wsa/testws");
			TEST_AJAX_URI= new URI("http://localhost:8080/aja/testajax");
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
		CometManagerI mnr1 = f.addManager(ac, "websocket", "testws");//manager a
		CometManagerI mnr2 = f.addManager(ac, "ajax", "testajax");//manager a
		

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
