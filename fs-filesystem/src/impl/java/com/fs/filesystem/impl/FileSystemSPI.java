/**
 * Jul 7, 2012
 */
package com.fs.filesystem.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.factory.ConfigFactoryI;
import com.fs.commons.api.support.SPISupport;

/**
 * @author wu
 * 
 */
public class FileSystemSPI extends SPISupport {

	/** */
	public FileSystemSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		ac.getContainer().find(ConfigFactoryI.class).newPopulator().spi(this)
				.active(ac).type("Object").force(true).populate();

	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
