/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.endpoint;

import com.fs.commons.api.message.MessageI;
import com.fs.expector.api.endpoint.EndpointI;
import com.fs.expector.impl.dg.support.MemberPropertiesDWSupport;

/**
 * @author wuzhen
 * 
 */
public class WebSocketEndpointImpl extends MemberPropertiesDWSupport implements
		EndpointI {

	// for get from DgMapI.
	public WebSocketEndpointImpl() {

	}

	// for create new one.
	public WebSocketEndpointImpl(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.expector.api.endpoint.EndpointI#sendMessage(com.fs.commons.api
	 * .message.MessageI)
	 */
	@Override
	public void sendMessage(MessageI msg) {

	}

}
