/**
 * Jun 24, 2012
 */
package com.fs.uiclient.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;

/**
 * @author wu
 * 
 */
public class UiClientImplSPI extends SPISupport {

	/** */
	public UiClientImplSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
