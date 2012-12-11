/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webserver.impl.jetty.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import com.fs.commons.api.lang.FsException;
import com.fs.webserver.api.websocket.WebSocketI;

/**
 * @author wu
 * 
 */
public class JettyWebSocketImpl extends WebSocketAdapter implements WebSocketI {

	protected String id;

	protected List<ListenerI> listeners;

	public JettyWebSocketImpl(String id) {
		this.id = id;
		this.listeners = new ArrayList<ListenerI>();
	}

	@Override
	public void onWebSocketText(String msg) {
		for(ListenerI l:this.listeners){
			l.onMessage(msg);
		}
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public String getId() {
		//
		return this.id;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void sendMessage(String msg) {
		try {
			getBlockingConnection().write(msg);
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void addListener(ListenerI ln) {
		//
		this.listeners.add(ln);
	}
}
