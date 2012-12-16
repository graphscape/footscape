/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 14, 2012
 */
package com.fs.expector.impl.handler;

import com.fs.commons.api.ActiveContext;
import com.fs.engine.api.support.HandlerSupport;
import com.fs.expector.api.GridFacadeI;
import com.fs.expector.api.GridedObjectManagerI;
import com.fs.expector.api.gobject.WebSocketGoI;
import com.fs.expector.api.session.SessionManagerI;

/**
 * @author wu
 * 
 */
public class WsMsgReseiveEventHandler extends HandlerSupport {

	protected GridFacadeI facade;

	protected SessionManagerI sessionManager;

	protected GridedObjectManagerI<WebSocketGoI> goManager;

	/*
	 * Dec 14, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.facade = this.container.find(GridFacadeI.class, true);
		this.sessionManager = this.container.find(SessionManagerI.class, true);
		this.goManager = this.facade.getWebSocketGridedObjectManager();
	}

}
