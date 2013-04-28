/**
 *  Dec 11, 2012
 */
package com.fs.websocket.impl.jetty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsCreatingInterceptorI;
import com.fs.websocket.api.WsListenerI;
import com.fs.websocket.api.WsManagerI;
import com.fs.websocket.api.support.CollectionWsListener;

/**
 * @author wuzhen
 *         <p>
 *         1-1 mapping to Servlet
 */
public class JettyWsManagerImpl extends ConfigurableSupport implements
		WsManagerI, WebSocketCreator, WsListenerI {

	protected CollectionWsListener listeners;// user listener

	protected List<WsCreatingInterceptorI> interceptors;

	protected String name;

	protected WebSocketServletFactory factory;

	protected Map<String, WebSocketI> socketMap;

	public JettyWsManagerImpl(String name) {
		this.name = name;
		this.listeners = new CollectionWsListener();
		this.socketMap = Collections
				.synchronizedMap(new HashMap<String, WebSocketI>());
		this.interceptors = new ArrayList<WsCreatingInterceptorI>();
	}

	@Override
	public void addListener(WsListenerI ln) {
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
	public WebSocketI getSocket(String id) {
		//
		return this.getSocket(id, false);

	}

	@Override
	public WebSocketI getSocket(String id, boolean force) {
		WebSocketI rt = this.socketMap.get(id);
		if (rt == null && force) {
			throw new FsException("no websocket:" + id);
		}
		return rt;
	}

	@Override
	public Object createWebSocket(UpgradeRequest arg0, UpgradeResponse arg1) {

		String id = this.nextId();

		JettyWebSocketImpl rt = new JettyWebSocketImpl(id,arg0);

		rt.addListener(this);//
		rt.addListener(this.listeners);//
		for (WsCreatingInterceptorI ci : this.interceptors) {
			ci.afterWsCreated(rt);
		}

		return rt;

	}

	protected String nextId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * Dec 11, 2012
	 */
	public void configure(WebSocketServletFactory factory) {
		//
		this.factory = factory;
		this.factory.setCreator(this);

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public List<WebSocketI> getSocketList() {
		//
		return new ArrayList<WebSocketI>(this.socketMap.values());
	}

	/*
	 * Dec 12, 2012
	 */

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		//
		WebSocketI old = this.socketMap.remove(ws.getId());
		if (old == null) {
			throw new FsException("bug,no this websocket:" + ws.getId());
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(WebSocketI ws, String ms) {
		//

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		//

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {
		this.socketMap.put(ws.getId(), ws);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.WsManagerI#addInterceptor(com.fs.websocket.api.
	 * WsCreatingInterceptorI)
	 */
	@Override
	public void addInterceptor(WsCreatingInterceptorI ci) {
		this.interceptors.add(ci);
	}
}
