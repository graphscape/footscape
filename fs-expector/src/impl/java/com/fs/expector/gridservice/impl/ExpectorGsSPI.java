/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.expector.gridservice.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.support.SPISupport;
import com.fs.gridservice.commons.api.EventDispatcherI;
import com.fs.gridservice.commons.api.GlobalEventDispatcherI;

/**
 * @author wu
 * 
 */
public class ExpectorGsSPI extends SPISupport {

	/**
	 * @param id
	 */
	public ExpectorGsSPI(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		ac.active("authProvider");
		// Converter factory
		ac.active("onlineNotifyService");
		this.activeConfirmCodeNotifier(ac);
		this.activeHandlers(ac);
		

	}

	protected void activeHandlers(ActiveContext ac) {
		EventDispatcherI ged = ac.getContainer().find(
				GlobalEventDispatcherI.class, true);

		Configuration cfg = Configuration.properties(this.id
				+ ".globalEventDispatcher");

		ged.getEngine()
				.getDispatcher()
				.addHandlers(
						cfg.getId(),
						cfg.getPropertyAsCsv("handlers").toArray(
								new String[] {}));

	}

	protected void activeConfirmCodeNotifier(ActiveContext ac) {

		ConfigFactoryI cf = ac.getContainer().find(ConfigFactoryI.class, true);

		PopulatorI pp = cf.newPopulator().container(ac.getContainer())
				.type("confirmCodeNotifier");
		pp.spi(this).active(ac).force(true).populate();

	}

	/* */
	@Override
	public void doDeactive(ActiveContext ac) {
	}

}
