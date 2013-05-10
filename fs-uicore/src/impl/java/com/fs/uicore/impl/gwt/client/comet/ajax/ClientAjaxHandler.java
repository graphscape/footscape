/**
 *  
 */
package com.fs.uicore.impl.gwt.client.comet.ajax;


/**
 * @author wu
 * 
 */
public abstract class ClientAjaxHandler {
	protected AjaxGomet client;

	public ClientAjaxHandler(AjaxGomet client) {
		this.client = client;
	}

	public abstract void handle(ClientAjaxMsgContext amc);
}
