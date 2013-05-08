/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.lang.FsException;

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
		Writer writer = amc.getWriter();
		try {
			writer.write("[");
			try {
				this.handlerInternal(amc);
			} finally {
				writer.write("]");
				writer.flush();
			}
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	protected void fetchMessage(AjaxMsgContext amc) {

		while (true) {

			AjaxMsg msg = null;
			try {
				msg = amc.as.getQueue().poll(amc.as.getTimeoutMs(), TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				continue;
			}

			if (msg == null) {// timeout
				break;
			}

			amc.write(msg);

		}
	}

	public abstract void handlerInternal(AjaxMsgContext amc);
}
