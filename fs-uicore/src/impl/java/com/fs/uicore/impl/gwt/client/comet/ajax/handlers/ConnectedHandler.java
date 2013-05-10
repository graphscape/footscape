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
public class ConnectedHandler extends ClientAjaxHandler{

	/**
	 * @param client
	 */
	public ConnectedHandler(AjaxGomet client) {
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
