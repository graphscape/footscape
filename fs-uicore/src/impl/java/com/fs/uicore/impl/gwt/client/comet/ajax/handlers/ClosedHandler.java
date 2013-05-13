/**
 *  
 */
package com.fs.uicore.impl.gwt.client.comet.ajax.handlers;

import com.fs.uicore.impl.gwt.client.comet.ajax.AjaxGomet;
import com.fs.uicore.impl.gwt.client.comet.ajax.ClientAjaxHandler;
import com.fs.uicore.impl.gwt.client.comet.ajax.ClientAjaxMsgContext;

/**
 * @author wu
 *
 */
public class ClosedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ClosedHandler(AjaxGomet client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		this.client.tryClosedByServer();
	}

}
