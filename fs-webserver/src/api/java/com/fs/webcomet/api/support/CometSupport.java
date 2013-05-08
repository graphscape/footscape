/**
 *  
 */
package com.fs.webcomet.api.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fs.webcomet.api.CometI;
import com.fs.webcomet.api.CometListenerI;
import com.fs.webcomet.api.support.CollectionCometListener;

/**
 * @author wu
 * 
 */
public abstract class CometSupport extends CollectionCometListener implements CometI {

	private String id;

	private String protocol;

	public CometSupport(String pro, String tid) {
		this.id = tid;
		this.protocol = pro;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
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

	@Override
	public String getProtocol() {
		return this.protocol;
	}

}
