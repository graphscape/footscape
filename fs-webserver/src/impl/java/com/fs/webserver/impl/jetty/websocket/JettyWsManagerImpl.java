/**
 *  Dec 11, 2012
 */
package com.fs.webserver.impl.jetty.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.webserver.api.websocket.WsListenerI;
import com.fs.webserver.api.websocket.WsManagerI;
import com.fs.webserver.api.websocket.WebSocketI;

/**
 * @author wuzhen
 *         <p>
 *         1-1 mapping to Servlet
 */
public class JettyWsManagerImpl extends ConfigurableSupport implements
		WsManagerI, WebSocketCreator {

	protected List<WsListenerI> listeners;

	protected String name;

	protected WebSocketServletFactory factory;

	protected Map<String, WebSocketI> socketMap;

	public JettyWsManagerImpl(String name) {
		this.name = name;
		this.listeners = new ArrayList<WsListenerI>();
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
		return null;
	}

	@Override
	public Object createWebSocket(UpgradeRequest arg0, UpgradeResponse arg1) {

		String id = this.nextId();

		JettyWebSocketImpl rt = new JettyWebSocketImpl(id);
		return rt;

	}

	protected String nextId() {
		UUID uuid = UUID.randomUUID();
		return uuid.getMostSignificantBits() + "-"
				+ uuid.getLeastSignificantBits();
	}

	/**
	 * Dec 11, 2012
	 */
	public void configure(WebSocketServletFactory factory) {
		//
		this.factory = factory;
		this.factory.setCreator(this);

	}

}
