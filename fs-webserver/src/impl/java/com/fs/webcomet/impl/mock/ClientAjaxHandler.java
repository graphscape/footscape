/**
 *  
 */
package com.fs.webcomet.impl.mock;

/**
 * @author wu
 * 
 */
public abstract class ClientAjaxHandler {
	protected MockAjaxClientImpl client;

	public ClientAjaxHandler(MockAjaxClientImpl client) {
		this.client = client;
	}

	public abstract void handle(ClientAjaxMsgContext amc);
}
