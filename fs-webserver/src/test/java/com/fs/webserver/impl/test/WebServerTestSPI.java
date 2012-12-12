/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl.test;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.SPISupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;
import com.fs.webserver.api.websocket.WsListenerI;
import com.fs.webserver.api.websocket.WebSocketI;
import com.fs.webserver.api.websocket.WsFactoryI;
import com.fs.webserver.api.websocket.WsManagerI;

/**
 * @author wu
 * 
 */
public class WebServerTestSPI extends SPISupport {

	private static Logger LOG = Log.getLog();//

	/** */
	public WebServerTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		this.activeForTestingWebServer(ac);
		this.activeForTestingWebSocket(ac);
	}

	public void activeForTestingWebServer(ActiveContext ac) {
		WebServerI ws = ac.getContainer().find(WebServerI.class);

		// WebAppI wa = ws.getWebApp("ROOT");
		WebAppI wa = ws.addWebApp(ac, "TestAPP", this.id + ".WebApp.TESTAPP");

		wa.addServlet(ac, "TEST_HOLDER", this.getId()
				+ ".servletHolder.TEST_HOLDER");// app
		//
		wa.addResource(ac, "testres", this.getId() + ".webResource.test");
		//
		try {
			wa.addResource(ac, "testResourceNotFound", this.id
					+ ".webResource.testResourceNotFound");
		} catch (FsException e) {
			if (e.getMessage().contains("notfound.jar")) {
				LOG.info("just testing,expected exception:" + e.getMessage());

			} else {
				throw e;
			}
		}
	}

	public void activeForTestingWebSocket(ActiveContext ac) {
		WsFactoryI f = ac.getContainer().find(WsFactoryI.class, true);
		WsManagerI mnr = f.addManager(ac, "testws");

	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
