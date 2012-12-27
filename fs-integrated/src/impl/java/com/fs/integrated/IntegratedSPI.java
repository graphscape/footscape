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
		WebAppI wa = ac.getContainer().find(WebServerI.class, true)
				.addWebApp(ac, "INTEGRATED", this.id + ".WebApp.INTEGRATED");

		// TODO create a integrated web app
		wa.addResource(ac, "INTEGRATED", this.id + ".webResource.integrated");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.SPISupport#doDeactive(com.fs.commons.api.
	 * ActiveContext)
	 */
	@Override
	protected void doDeactive(ActiveContext ac) {
		// TODO Auto-generated method stub

	}

}
