/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.support.SPISupport;
import com.fs.expector.api.wrapper.WsMsgReceiveEW;
import com.fs.expector.api.wrapper.WsMsgSendEW;
import com.fs.expector.impl.converter.EventWrapperC;

/**
 * @author wuzhen
 * 
 */
public class ExpectorSPI extends SPISupport {

	/**
	 * @param id
	 */
	public ExpectorSPI(String id) {
		super(id);
	}

	@Override
	public void doActive(ActiveContext ac) {
		ConverterI.FactoryI cf = ac.getContainer().find(ConverterI.FactoryI.class, true);

		cf.addConverter(new EventWrapperC(WsMsgReceiveEW.class, cf));
		cf.addConverter(new EventWrapperC(WsMsgSendEW.class, cf));

		ac.active("gridMember");
		ac.active("gridFacade");
		ac.active("webSocketFactory");
		ac.active("webSocketGoManager");
		ac.active("sessionManager");
		ac.active("globalEventDispatcher");
		ac.active("localEventDispatcher");
		

	}

	@Override
	protected void doDeactive(ActiveContext ac) {

	}

}
