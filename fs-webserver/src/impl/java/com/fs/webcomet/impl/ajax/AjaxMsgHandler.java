/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax;

import java.util.Map;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public abstract class AjaxMsgHandler {
	protected Map<String, AjaxComet> sessionMap;
	protected AjaxCometManagerImpl manager;
	protected boolean sessionRequired;

	public AjaxMsgHandler(boolean sr, Map<String, AjaxComet> sessionMap, AjaxCometManagerImpl manager) {
		this.sessionMap = sessionMap;
		this.manager = manager;
		this.sessionRequired = sr;
	}

	public void handle(AjaxMsgContext amc) {
		if (this.sessionRequired && amc.arc.as == null) {
			throw new FsException("session required for handler:" + this);
		}
		
		this.handlerInternal(amc);

	}

	public abstract void handlerInternal(AjaxMsgContext amc);
}
