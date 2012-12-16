/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 14, 2012
 */
package com.fs.expector.impl.handler;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.message.MessageI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.engine.api.support.HandlerSupport;
import com.fs.expector.api.GridedObjectManagerI;
import com.fs.expector.api.gobject.WebSocketGoI;
import com.fs.expector.api.session.SessionManagerI;
import com.fs.expector.api.wrapper.WsMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class WebSocketMessageSendingEventHandler extends HandlerSupport {

	protected SessionManagerI sessionManager;

	protected GridedObjectManagerI<WebSocketGoI> goManager;

	@Handle("event")
	// local event
	public void handleEvent(WsMsgReceiveEW evt, RequestI req, ResponseI res) {

		String to = null;// evt.getToEndpointId();

		WebSocketGoI wgo = this.goManager.getGridedObject(to);//

		if (wgo == null) {// closed?
			return;// ignore?
		}

		MessageI msg = evt.getMessage();

		wgo.sendMessage(msg);
		// ep.sendMessage(evt);
	}

	/*
	 * Dec 14, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);

	}

}
