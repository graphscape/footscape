/**
 *  
 */
package com.fs.webcomet.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.SPISupport;
import com.fs.webcomet.api.CometFactoryI;
import com.fs.webcomet.api.CometProtocolI;
import com.fs.webcomet.impl.ajax.AjaxCometProtocol;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;

/**
 * @author wu
 * 
 */
public class WebCometSPI extends SPISupport {

	/**
	 * @param id
	 */
	public WebCometSPI(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.SPISupport#doActive(com.fs.commons.api.
	 * ActiveContext)
	 */
	@Override
	public void doActive(ActiveContext ac) {
		
		WebServerI ws = ac.getContainer().find(WebServerI.class, true);

		//add the ajax web app
		WebAppI aja = ws.addWebApp(ac, "aja", this.getId() + ".WebApp.AJA");
		
		ac.activitor().context(ac).spi(this).cfgId(this.getId() + ".Object.COMET_FACTORY")
				.object(new CometFactoryImpl()).active();

		// register ajax comet protocol
		CometFactoryI cf = ac.getContainer().find(CometFactoryI.class, true);
		CometProtocolI cp = new AjaxCometProtocol("ajax", Configuration.properties(this.id
				+ ".AjaxCometProtocol"));

		cf.addProtocol(cp);
		// add the default manager
		cf.addManager(ac, "ajax", "default");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.SPISupport#doBeforeShutdown(int)
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		// TODO Auto-generated method stub

	}

}
