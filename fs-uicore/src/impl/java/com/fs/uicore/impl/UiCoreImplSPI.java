/**
 * Jun 24, 2012
 */
package com.fs.uicore.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;

/**
 * @author wu
 * 
 */
public class UiCoreImplSPI extends SPISupport {

	/** */
	public UiCoreImplSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		// ac.getContainer().find(ConfigFactoryI.class).newPopulator().active(ac)
		// .type("Object").populate();
		// add a servlet
		/*
		 * WebServerI ws = ac.getContainer().find(WebServerI.class, true);
		 * WebAppI wa = ws.getWebApp("ROOT");
		 */

		WebAppI wa = ac.getContainer().find(WebServerI.class, true)
				.addWebApp(ac, "UICORE", this.getId() + ".WebApp.UICORE");

		this.activeHandlers(ac);
	}

	private void activeHandlers(ActiveContext ac) {

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
