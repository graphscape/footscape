/**
 * Jul 10, 2012
 */
package com.fs.websocket.impl;

import java.net.URI;
import java.net.URISyntaxException;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.SPISupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.impl.jetty.JettyWsFactoryImpl;

/**
 * @author wu
 * 
 */
public class WebSocketSPI extends SPISupport {

	/** */
	public WebSocketSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {

		// ADD default ROOT webapp
		WebServerI ws = ac.getContainer().find(WebServerI.class, true);

		// Add default websocket app
		WebAppI wsa = ws.addWebApp(ac, "wsa", this.getId() + ".WebApp.WSA");
		// Add ManagerFactory
		ac.activitor().context(ac).spi(this).cfgId(this.getId() + ".Object.WS_FACTORY")
				.object(new JettyWsFactoryImpl()).active();
		WsFactoryI wsf = ac.getContainer().find(WsFactoryI.class);
		wsf.addManager(ac, "default");//

	}

	/* */
	@Override
	public void doDeactive(ActiveContext ac) {
	}

}
