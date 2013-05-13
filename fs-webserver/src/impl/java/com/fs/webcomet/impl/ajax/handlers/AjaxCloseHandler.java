/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax.handlers;

import com.fs.commons.api.session.SessionManagerI;
import com.fs.webcomet.impl.ajax.AjaxCometManagerImpl;
import com.fs.webcomet.impl.ajax.AjaxMsgContext;
import com.fs.webcomet.impl.ajax.AjaxMsgHandler;

/**
 * @author wu
 * 
 */
public class AjaxCloseHandler extends AjaxMsgHandler {

	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxCloseHandler(SessionManagerI sessionMap, AjaxCometManagerImpl manager) {
		super(true, sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		amc.arc.writeCloseSuccess();
		if (amc.arc.as == null) {
			// ignore the session already closed.
			return;
		}
		//TODO remove the session from manager.
		//this.sessionMap.
		// fetch?
		// this.fetchMessage(amc);
		this.manager.onClose(amc.arc.as, 0, "");

	}
}
