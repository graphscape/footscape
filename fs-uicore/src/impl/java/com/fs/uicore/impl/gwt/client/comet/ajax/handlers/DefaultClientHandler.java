/**
 *  
 */
package com.fs.uicore.impl.gwt.client.comet.ajax.handlers;


import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.fs.uicore.impl.gwt.client.comet.ajax.AjaxGomet;
import com.fs.uicore.impl.gwt.client.comet.ajax.ClientAjaxHandler;
import com.fs.uicore.impl.gwt.client.comet.ajax.ClientAjaxMsgContext;


/**
 * @author wu
 * 
 */
public class DefaultClientHandler extends ClientAjaxHandler {

	public static final UiLoggerI LOG = UiLoggerFactory.getLogger(DefaultClientHandler.class);

	/**
	 * @param client
	 */
	public DefaultClientHandler(AjaxGomet client) {
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
