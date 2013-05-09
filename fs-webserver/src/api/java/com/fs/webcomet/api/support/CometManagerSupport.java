/**
 *  Dec 11, 2012
 */
package com.fs.webcomet.api.support;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.StringUtil;
import com.fs.webcomet.api.CometCreatingInterceptorI;
import com.fs.webcomet.api.CometI;
import com.fs.webcomet.api.CometListenerI;
import com.fs.webcomet.api.CometManagerI;
import com.fs.webcomet.api.CometProtocolI;

/**
 * @author wuzhen
 *         <p>
 *         1-1 mapping to Servlet
 */
public class CometManagerSupport extends ConfigurableSupport implements
		CometManagerI, CometListenerI {

	protected CollectionCometListener listeners;// user listener

	protected List<CometCreatingInterceptorI> interceptors;

	protected String name;

	protected Map<String, CometI> socketMap;
	
	protected CometProtocolI protocol;

	public CometManagerSupport(CometProtocolI cp,String name) {
		this.name = name;
		this.protocol = cp;
		this.listeners = new CollectionCometListener();
		this.socketMap = Collections
				.synchronizedMap(new HashMap<String, CometI>());
		this.interceptors = new ArrayList<CometCreatingInterceptorI>();
	}

	@Override
	public void addListener(CometListenerI ln) {
		//
		if (this.isAttached()) {
			throw new FsException("not supported for online add");
		}
		this.listeners.add(ln);
	}

	@Override
	public String getName() {

		return name;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public CometI getSocket(String id) {
		//
		return this.getSocket(id, false);

	}

	@Override
	public CometI getSocket(String id, boolean force) {
		CometI rt = this.socketMap.get(id);
		if (rt == null && force) {
			throw new FsException("no websocket:" + id);
		}
		return rt;
	}

	protected String nextId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public List<CometI> getSocketList() {
		//
		return new ArrayList<CometI>(this.socketMap.values());
	}

	/*
	 * Dec 12, 2012
	 */

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		//
		CometI old = this.socketMap.remove(ws.getId());
		if (old == null) {
			throw new FsException("bug,no this websocket:" + ws.getId());
		}
		this.listeners.onClose(ws, statusCode, reason);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(CometI ws, String ms) {
		this.listeners.onMessage(ws, ms);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		//
		this.listeners.onException(ws, t);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(CometI ws) {
		this.socketMap.put(ws.getId(), ws);//
		this.listeners.onConnect(ws);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.WsManagerI#addInterceptor(com.fs.websocket.api.
	 * WsCreatingInterceptorI)
	 */
	@Override
	public void addInterceptor(CometCreatingInterceptorI ci) {
		this.interceptors.add(ci);
	}

	/* (non-Javadoc)
	 * @see com.fs.websocket.api.WsListenerI#onMessage(com.fs.websocket.api.WebSocketI, java.io.Reader)
	 */
	@Override
	public void onMessage(CometI ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}

	/* (non-Javadoc)
	 * @see com.fs.webcomet.api.CometManagerI#getProtocol()
	 */
	@Override
	public CometProtocolI getProtocol() {
		return this.protocol;
	}
}
