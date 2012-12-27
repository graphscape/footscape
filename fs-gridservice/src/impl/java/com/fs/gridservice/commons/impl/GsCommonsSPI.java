/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.support.SPISupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;
import com.fs.gridservice.commons.impl.converter.EventWrapperC;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;

/**
 * @author wuzhen
 * 
 */
public class GsCommonsSPI extends SPISupport {

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

		// objects
		ac.active("gridMember");
		ac.active("gridFacade");

		ac.active("webSocketFactory");
		ac.active("webSocketGoManager");

		ac.active("terminalManager");
		ac.active("clientManager");
		ac.active("presenceManager");
		ac.active("participantManager");
		ac.active("chatGroupManager");

		ac.active("sessionManager");
		ac.active("globalEventDispatcher");
		ac.active("localEventDispatcher");
		// servlet
		WebAppI wa = ac.getContainer().find(WebServerI.class, true)
				.addWebApp(ac, "GS", this.getId() + ".webApp.GS");
		wa.addServlet(ac, "terminal", this.getId() + ".servletHolder.terminal");
	}

	@Override
	protected void doDeactive(ActiveContext ac) {

	}

}
