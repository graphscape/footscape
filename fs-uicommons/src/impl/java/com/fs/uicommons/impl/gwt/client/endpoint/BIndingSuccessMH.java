/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.endpoint;

import com.fs.uicommons.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class BIndingSuccessMH implements MessageHandlerI {

	protected EndpointWsImpl endpoint;

	public BIndingSuccessMH(EndpointWsImpl ep) {
		this.endpoint = ep;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(MessageData t) {
		endpoint.bindingSuccess();
	}

}
