/**
 *  
 */
package com.fs.webcomet.impl.mock.handlers;

import com.fs.webcomet.impl.mock.ClientAjaxHandler;
import com.fs.webcomet.impl.mock.ClientAjaxMsgContext;
import com.fs.webcomet.impl.mock.MockAjaxClientImpl;

/**
 * @author wu
 *
 */
public class ClosedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ClosedHandler(MockAjaxClientImpl client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		this.client.closedByServer();
	}

}
