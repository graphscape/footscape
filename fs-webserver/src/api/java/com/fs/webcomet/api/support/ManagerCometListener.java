/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.fs.webcomet.api.support;

import com.fs.webcomet.api.CometFactoryI;
import com.fs.webcomet.api.CometI;
import com.fs.webcomet.api.CometManagerI;

/**
 * @author wu
 * 
 */
public class ManagerCometListener extends AbstractCometListener {

	protected CometFactoryI factory;

	protected String name;

	protected CometManagerI manager;

	public ManagerCometListener(CometFactoryI wf, String manager) {
		this.factory = wf;
		this.name = manager;
	}

	public void start() {
		this.manager = this.factory.getManager(this.name, true);
		this.manager.addListener(this);
		if (LOG.isDebugEnabled()) {
			LOG.debug("started manager ws listener:" + this.name);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(CometI ws, String ms) {
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(CometI ws, Throwable t) {

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(CometI ws) {
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
	}

}
