/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 14, 2012
 */
package com.fs.expector.impl.handler;

import com.fs.commons.api.ActiveContext;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.engine.api.support.HandlerSupport;
import com.fs.expector.api.GridedObjectManagerI;
import com.fs.expector.api.data.EventGd;
import com.fs.expector.api.data.WebSocketRefGd;
import com.fs.expector.api.gobject.WebSocketGoI;
import com.fs.expector.api.session.SessionManagerI;

/**
 * @author wu
 * 
 */
public class EndpointEventHandler extends HandlerSupport {

	protected SessionManagerI sessionManager;

	protected GridedObjectManagerI<WebSocketGoI> goManager;

	@Handle("consume")
	public void handleConsume(EventGd evt, RequestI req, ResponseI res) {
		
		String to = null;// evt.getToEndpointId();
		
		WebSocketRefGd ep = null;
		
		WebSocketGoI wgo = this.goManager.getGridedObject(to);//
		
		if (ep == null) {// closed?
			return;// ignore?
		}
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
