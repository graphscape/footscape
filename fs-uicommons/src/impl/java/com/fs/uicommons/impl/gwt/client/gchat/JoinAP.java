/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicore.api.gwt.client.UiRequest;

/**
 * @author wu
 * 
 */
public class JoinAP extends APSupport {

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		// c.getClient(true).getChild(MessageDispatcherI.FactoryI.class,
		// false).get(0);
		EndPointI ep = c.getClient(true).getChild(EndPointI.class, true);
		// ep.s
		((GChatControlI) c).join();

	}

}
