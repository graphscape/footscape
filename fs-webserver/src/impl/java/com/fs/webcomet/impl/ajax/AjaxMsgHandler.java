/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.session.SessionManagerI;

/**
 * @author wu
 * 
 */
public abstract class AjaxMsgHandler {
	protected SessionManagerI sessionMap;
	protected AjaxCometManagerImpl manager;
	protected boolean sessionRequired;

	public AjaxMsgHandler(boolean sr, SessionManagerI sessionMap, AjaxCometManagerImpl manager) {

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
