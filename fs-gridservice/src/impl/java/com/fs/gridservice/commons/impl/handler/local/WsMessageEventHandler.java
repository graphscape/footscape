/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 17, 2012
 */
package com.fs.gridservice.commons.impl.handler.local;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.wrapper.WsMsgSendEW;
import com.fs.gridservice.commons.impl.support.WsMsgReseiveEventHandler;

/**
 * @author wu
 * 
 */
public class WsMessageEventHandler extends WsMsgReseiveEventHandler {

	/*
	 * Dec 16, 2012
	 */
	@Handle("send")
	public void handleSend(WsMsgSendEW reqE, RequestI req) {
		//
		MessageI msg = reqE.getMessage();

		String wsId = reqE.getWebSocketId();
		WebSocketGoI wso = this.facade.getWebSocketGridedObjectManager()
				.getGridedObject(wsId, true);
		wso.sendMessage(msg);

	}
}