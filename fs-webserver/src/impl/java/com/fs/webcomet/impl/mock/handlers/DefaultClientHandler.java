/**
 *  
 */
package com.fs.webcomet.impl.mock.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.webcomet.impl.mock.ClientAjaxHandler;
import com.fs.webcomet.impl.mock.ClientAjaxMsgContext;
import com.fs.webcomet.impl.mock.MockAjaxClientImpl;

/**
 * @author wu
 * 
 */
public class DefaultClientHandler extends ClientAjaxHandler {

	public static final Logger LOG = LoggerFactory.getLogger(DefaultClientHandler.class);

	/**
	 * @param client
	 */
	public DefaultClientHandler(MockAjaxClientImpl client) {
		super(client);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl
	 * .mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		LOG.info("amc:" + amc);
	}

}
