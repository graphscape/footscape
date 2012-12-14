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
public class EndpointImpl extends MemberPropertiesDWSupport implements
		EndpointI {

	// for get from DgMapI.
	public EndpointImpl() {

	}

	// for create new one.
	public EndpointImpl(String id) {
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
