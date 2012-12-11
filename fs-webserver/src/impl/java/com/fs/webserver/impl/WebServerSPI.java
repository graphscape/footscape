/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;
import com.fs.webserver.impl.jetty.JettyWebServerImpl;

/**
 * @author wu
 * 
 */
public class WebServerSPI extends SPISupport {

	/** */
	public WebServerSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		{
			JettyWebServerImpl ws = new JettyWebServerImpl();
			ac.activitor().context(ac).spi(this)
					.cfgId(this.getId() + ".Object.WEB_SERVER").object(ws)
					.active();
			//ws.start();not start here
		}
		//
		WebServerI ws = ac.getContainer().find(WebServerI.class, true);

		WebAppI wa = ws.addWebApp(ac,"ROOT", this.getId() + ".WebApp.ROOT");// ROOT web
																		// app
	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
