/**
 *  
 */
package com.fs.webcomet.impl.ajax;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fs.commons.api.lang.FsException;
import com.fs.webcomet.api.CometListenerI;
import com.fs.webcomet.api.support.CometSupport;

/**
 * @author wu
 * 
 */
public class AjaxComet extends CometSupport {

	private BlockingQueue<AjaxMsg> queue;

	private long timeoutMs = 1000;

	public AjaxComet(String tid) {
		super("ajax", tid);
		this.queue = new LinkedBlockingQueue<AjaxMsg>();
	}

	@Override
	public void sendMessage(String msg) {
		AjaxMsg am = new AjaxMsg(AjaxMsg.MESSAGE);
		am.setProperty(AjaxMsg.PK_TEXTMESSAGE, msg);
		this.putAjaxMessage(am);
	}
	
	public BlockingQueue<AjaxMsg> getQueue(){
		return queue;
	}

	public void putAjaxMessage(AjaxMsg am) {
		//
		try {
			this.queue.put(am);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}//
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.websocket.api.WebSocketI#addListener(com.fs.websocket.api.WsListenerI
	 * )
	 */
	@Override
	public void addListener(CometListenerI ln) {
		this.addListener(ln);
	}

	/**
	 * @return the timeoutMs
	 */
	public long getTimeoutMs() {
		return timeoutMs;
	}

}
