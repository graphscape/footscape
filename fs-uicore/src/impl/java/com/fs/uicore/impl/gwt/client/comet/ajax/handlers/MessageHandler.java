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
public class MessageHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public MessageHandler(AjaxGomet client) {
		super(client);
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.impl.mock.ClientAjaxHandler#handle(com.fs.webcomet.impl.mock.ClientAjaxMsgContext)
	 */
	@Override
	public void handle(ClientAjaxMsgContext amc) {
		String msg = amc.am.getProperty(AjaxMsg.PK_TEXTMESSAGE,true);
		this.client.messageFromServer(msg);
		
	}

}
