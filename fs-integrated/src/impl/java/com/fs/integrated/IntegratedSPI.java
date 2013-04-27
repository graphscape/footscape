/**
 * Jul 14, 2012
 */
package com.fs.integrated;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;

/**
 * @author wu
 * 
 */
public class IntegratedSPI extends SPISupport {

	/** */
	public IntegratedSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		// add the compiled gwt js to web server
		// WebAppI wa = ac.getContainer().find(WebServerI.class, true)
		// .getWebApp("UICORE");
		WebServerI ws = ac.getContainer().find(WebServerI.class, true);
		{
			WebAppI wa = ws.addWebApp(ac, "INTEGRATED", this.id + ".WebApp.INTEGRATED");

			wa.addResource(ac, "INTEGRATED", this.id + ".webResource.integrated");
		}
		{
			WebAppI root = ws.getRootWebApp();
			root.addResource(ac, "ROOT", this.id + ".webResource.root");
			root.setWellcomeFiles(new String[] { "home.html", "integrated/fs.html" });

		}
	}

	/*
	 * Apr 7, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
