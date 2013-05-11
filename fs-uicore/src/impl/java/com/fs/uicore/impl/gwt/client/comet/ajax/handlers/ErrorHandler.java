/**
 *  
 */
package com.fs.uicore.impl.gwt.client.comet.ajax.handlers;

import com.fs.uicore.impl.gwt.client.comet.ajax.AjaxGomet;
import com.fs.uicore.impl.gwt.client.comet.ajax.AjaxMsg;
import com.fs.uicore.impl.gwt.client.comet.ajax.ClientAjaxHandler;
import com.fs.uicore.impl.gwt.client.comet.ajax.ClientAjaxMsgContext;

/**
 * @author wu
 * 
 */
public class ErrorHandler extends ClientAjaxHandler {

	/**
	 * @param client
	 */
	public ErrorHandler(AjaxGomet client) {
		super(client);
	}

	@Override
	public void handle(ClientAjaxMsgContext amc) {
		
		
		String errorCode = amc.am.getProperty(AjaxMsg.PK_ERROR_CODE,true);
		String errorMsg = amc.am.getProperty(AjaxMsg.PK_ERROR_MSG);
		
		this.client.errorFromServer(errorCode,errorMsg);
	}

}
