/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package com.fs.uicore.impl.gwt.client.endpoint.protocols;

import com.fs.uicore.api.gwt.client.endpoint.Address;
import com.fs.uicore.impl.gwt.client.comet.GometI;
import com.fs.uicore.impl.gwt.client.comet.ws.WsGomet;
import com.fs.uicore.impl.gwt.client.endpoint.EndpointImpl.ProtocolI;

/**
 * @author wu
 * 
 */
public class WsProtocolImpl implements ProtocolI {

	/*
	 * May 9, 2013
	 */
	@Override
	public GometI createGomet(Address uri, boolean force) {
		//
		WsGomet rt = new WsGomet(uri);
		
		return rt;

	}

}
