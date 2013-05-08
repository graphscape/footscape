/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax.handlers;

import java.util.Map;

import com.fs.webcomet.impl.ajax.AjaxComet;
import com.fs.webcomet.impl.ajax.AjaxCometManagerImpl;
import com.fs.webcomet.impl.ajax.AjaxMsg;
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

	public AjaxCloseHandler(Map<String, AjaxComet> sessionMap, AjaxCometManagerImpl manager) {
		super(sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		if (amc.as == null) {
			// ignore the session already closed.
			return;
		}
		//fetch?
		this.fetchMessage(amc);
		this.manager.onClose(amc.as, 0, "");

	}
}
