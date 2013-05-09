/**
 *  
 */
package com.fs.webcomet.impl.mock.handlers;

import com.fs.webcomet.impl.ajax.AjaxMsg;
import com.fs.webcomet.impl.mock.ClientAjaxHandler;
import com.fs.webcomet.impl.mock.ClientAjaxMsgContext;
import com.fs.webcomet.impl.mock.MockAjaxClientImpl;

/**
 * @author wu
 *
 */
public class MessageHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public MessageHandler(MockAjaxClientImpl client) {
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
