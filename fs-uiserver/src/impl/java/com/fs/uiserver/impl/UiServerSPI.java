/**
 * Jun 22, 2012
 */
package com.fs.uiserver.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.ServiceEngineI;

/**
 * @author wu
 * 
 */
public class UiServerSPI extends SPISupport {

	public static final String ENAME_UISERVER = "uiserver";

	/** */
	public UiServerSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		ac.active("authProvider");
		// Converter factory
		this.activeConfirmCodeNotifier(ac);
		this.activeHandlers(ac);

	}

	protected void activeHandlers(ActiveContext ac) {
		ConverterI.FactoryI cf = ac.getContainer().find(
				ConverterI.FactoryI.class, true);
		// addConverter.
		// filter provide the connection intercept.
		ServiceEngineI se = ac.getContainer().find(EngineFactoryI.class)
				.getEngine(ENAME_UISERVER);
		PopulatorI fp = se.populator("filter");
		fp.spi(this).active(ac).force(false).populate();

		// TODO remove if no handler?
		PopulatorI hp = se.getDispatcher().populator("handler");
		hp.active(ac).force(true).populate();
		// add node type
	}

	protected void activeConfirmCodeNotifier(ActiveContext ac) {

		// TODO move to ActiveContext or Container?

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
