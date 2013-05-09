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
public class ErrorHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ErrorHandler(MockAjaxClientImpl client) {
		super(client);
	}

	@Override
	public void handle(ClientAjaxMsgContext amc) {
		
		this.client.errorFromServer();
	}

}
