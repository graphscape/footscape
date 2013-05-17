/**
 * Jul 10, 2012
 */
package com.fs.websocket.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.SPISupport;
import com.fs.webcomet.api.CometFactoryI;
import com.fs.webcomet.api.CometProtocolI;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;
import com.fs.websocket.impl.jetty.JettyWsCometProtocol;

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

		// register websocket protocol
		CometFactoryI cf = ac.getContainer().find(CometFactoryI.class, true);
		CometProtocolI cp = new JettyWsCometProtocol("websocket", Configuration.properties(this.id
				+ ".WebSocketCometProtocol"));
		cf.addProtocol(cp);
		// add a default manager.
		cf.addManager(ac, "websocket", "default");//

		// flash policy file
		/*
		 * ac.activitor().context(ac).spi(this).name("FLASH_POLICY_SERVER").object
		 * (new FlashPolicyServer()) .active();
		 */

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//
		// ServerI fps = this.container.find(ServerI.class,
		// "FLASH_POLICY_SERVER", true);
		// fps.shutdown();
	}

}
