/**
 * Jun 24, 2012
 */
package com.fs.uicore.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.DispatcherI;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.ServiceEngineI;
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
		wa.addServlet(ac, "UIDATA_TRANSFER", this.getId()
				+ ".servletHolder.UIDATA_TRANSFER");
		this.activeHandlers(ac);
	}

	private void activeHandlers(ActiveContext ac) {
		ServiceEngineI engine0 = ac.getContainer()
				.find(EngineFactoryI.class, true).getEngine("uiserver");
		PopulatorI fp = engine0.populator("filter");

		fp.spi(this).active(ac).force(false).populate();
		// TODO remove if no handler?
		PopulatorI hp = engine0.getDispatcher().populator("handler");
		hp.active(ac).force(true).populate();
	}

	/* */
	@Override
	public void doDeactive(ActiveContext ac) {
	}

}
