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
public class ConnectedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ConnectedHandler(MockAjaxClientImpl client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		String sid = amc.am.getSessionId(true);
		this.client.conected(sid);
		
	}

}
