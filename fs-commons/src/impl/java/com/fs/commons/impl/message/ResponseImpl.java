/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.message;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.value.ErrorInfos;

/**
 * @author wuzhen
 * 
 */
public class ResponseImpl extends MessageSupport implements ResponseI {

	private MessageI request;

	public ResponseImpl(MessageI req) {
		this.request = req;
		this.setHeader(HK_SOURCE_ID, req.getId());
		
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.engine.api.ResponseI#getRequest()
	 */
	@Override
	public MessageI getRequest() {
		// TODO Auto-generated method stub
		return this.request;
	}

}
