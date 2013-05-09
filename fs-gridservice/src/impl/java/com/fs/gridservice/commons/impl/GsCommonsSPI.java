/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.support.SPISupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;
import com.fs.gridservice.commons.api.wrapper.internal.InternalMsgEW;
import com.fs.gridservice.commons.impl.converter.EventWrapperC;

/**
 * @author wuzhen
 * 
 */
public class GsCommonsSPI extends SPISupport {

	public static final int shutdownLoop = 20;

	/**
	 * @param id
	 */
	public GsCommonsSPI(String id) {
		super(id);
	}

	@Override
	public void doActive(ActiveContext ac) {
		// converters
		ConverterI.FactoryI cf = ac.getContainer().find(ConverterI.FactoryI.class, true);

		cf.addConverter(new EventWrapperC(TerminalMsgReceiveEW.class, cf));
		cf.addConverter(new EventWrapperC(TerminalMsgSendEW.class, cf));
		cf.addConverter(new EventWrapperC(InternalMsgEW.class, cf));

		// objects
		ac.active("gridMember");
		ac.active("gridFacade");

		ac.active("cometListener");
		ac.active("endPointGoManager");

		ac.active("terminalManager");
		ac.active("clientManager");
		ac.active("presenceManager");
		ac.active("participantManager");
		ac.active("chatGroupManager");

		ac.active("sessionManager");
		ac.active("globalEventDispatcher");
		ac.active("localEventDispatcher");

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
