/**
 *  
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONValue;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.webcomet.api.CometI#getProtocol()
	 */
	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the timeoutMs
	 */
	public long getTimeoutMs() {
		return timeoutMs;
	}

}
