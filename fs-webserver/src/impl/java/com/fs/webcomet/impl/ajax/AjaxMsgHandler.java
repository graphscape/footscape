/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax;

import java.util.Map;

/**
 * @author wu
 * 
 */
public abstract class AjaxMsgHandler {
	protected Map<String, AjaxComet> sessionMap;
	protected AjaxCometManagerImpl manager;

	public AjaxMsgHandler(Map<String, AjaxComet> sessionMap, AjaxCometManagerImpl manager) {
		this.sessionMap = sessionMap;
		this.manager = manager;
	}

	public void handle(AjaxMsgContext amc) {
		this.handlerInternal(amc);
	}

	public abstract void handlerInternal(AjaxMsgContext amc);
}
