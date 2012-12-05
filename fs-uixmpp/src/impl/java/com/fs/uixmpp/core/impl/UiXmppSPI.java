/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 16, 2012
 */
package com.fs.uixmpp.core.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;

/**
 * @author wu
 * 
 */
public class UiXmppSPI extends SPISupport {

	/**
	 * @param id
	 */
	public UiXmppSPI(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.ActivableI#active(com.fs.commons.api.ActiveContext)
	 */
	@Override
	public void active(ActiveContext ac) {
		WebAppI wa = ac.getContainer().find(WebServerI.class, true)
				.getWebApp("UICORE");
		wa.addServlet(ac, "BOSH_PROXY", this.getId()
				+ ".servletHolder.BOSH_PROXY");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.ActivableI#deactive(com.fs.commons.api.ActiveContext)
	 */
	@Override
	public void deactive(ActiveContext ac) {
		// TODO Auto-generated method stub

	}

}
