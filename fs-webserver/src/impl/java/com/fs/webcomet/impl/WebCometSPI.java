/**
 *  
 */
package com.fs.webcomet.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;

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
		ac.activitor().context(ac).spi(this).cfgId(this.getId() + ".Object.WS_FACTORY")
				.object(new CometFactoryImpl()).active();
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
