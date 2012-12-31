/**
 *  Dec 31, 2012
 */
package com.fs.commons.api.message;

import com.fs.commons.api.service.ServiceContext;

/**
 * @author wuzhen
 * 
 */
public class MessageContext extends ServiceContext<MessageI, ResponseI> {

	/**
	 * @param req
	 * @param res
	 */
	public MessageContext(MessageI req, ResponseI res) {
		super(req, res);
	}

}
