/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.impl.gwt.client.endpoint;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.endpoint.Address;
import com.fs.uicore.api.gwt.client.event.ClientClosingEvent;
import com.fs.uicore.api.gwt.client.html5.WebSocketJSO;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.support.EndpointSupport;
import com.fs.uicore.impl.gwt.client.comet.GometI;
import com.fs.uicore.impl.gwt.client.comet.ajax.AjaxGomet;
import com.fs.uicore.impl.gwt.client.comet.ws.WsGomet;
import com.fs.uicore.impl.gwt.client.endpoint.CometPPs.ProtocolPort;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class EndpointWsImpl extends EndpointSupport {

	public static interface ProtocolI {
		public GometI open(Address uri, boolean force);
	}

	public static class WsProtocol implements ProtocolI {

		/*
		 * May 9, 2013
		 */
		@Override
		public GometI open(Address uri, boolean force) {
			//

			String uriS = uri.getUri();

			WebSocketJSO wso = WebSocketJSO.newInstance(uriS, false);
			if (wso == null) {
				if (force) {
					throw new UiException("browser not support ws");
				}
				return null;
			}

			WsGomet rt = new WsGomet(wso);
			rt.open();
			return rt;

		}

	}

	public static class AjaxProtocol implements ProtocolI {

		private ContainerI c;

		public AjaxProtocol(ContainerI c) {
			this.c = c;
		}

		/*
		 * May 9, 2013
		 */
		@Override
		public GometI open(Address uri, boolean force) {
			//

			AjaxGomet rt = new AjaxGomet(this.c, uri);
			rt.open();
			return rt;

		}

	}

	private GometI socket;

	private Map<String, ProtocolI> protocols;

	/**
	 * @param md
	 */
	public EndpointWsImpl(ContainerI c,  MessageDispatcherI md) {
		super(c, md, new MessageCacheImpl(c));
		this.protocols = new HashMap<String, ProtocolI>();
		this.protocols.put("ws", new WsProtocol());
		this.protocols.put("wss", new WsProtocol());

		this.protocols.put("http", new AjaxProtocol(c));
		this.protocols.put("https", new AjaxProtocol(c));

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

	}

	/**
	 * Apr 4, 2013
	 */
	protected void onClientClosing(ClientClosingEvent t) {
		this.close();
	}

	@Override
	public void close() {
		this.socket.close();
	}

	@Override
	public void open(Address uri) {
		super.open(uri);

		String proS = uri.getProtocol();

		ProtocolI pro = this.protocols.get(proS);

		this.socket = pro.open(uri, false);
		if (this.socket == null) {
			Window.alert("protocol is not support:" + proS);
		}

		this.socket.onOpen(new HandlerI<GometI>() {

			@Override
			public void handle(GometI t) {
				//
				EndpointWsImpl.this.onConnected();
			}
		});
		this.socket.onMessage(new HandlerI<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointWsImpl.this.onMessage(t);

			}
		});
		this.socket.onClose(new HandlerI<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointWsImpl.this.onClosed(t, "");
			}
		});
		this.socket.onError(new HandlerI<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointWsImpl.this.onError(t);
			}
		});

	}

	@Override
	protected void assertNativeIsOpen() {
		if (this.socket == null) {
			throw new UiException("socket is null");
		}

		boolean rs = this.socket.isOpen();
		if (!rs) {
			throw new UiException("socket is not open,readyState:" + rs);
		}

	}

	@Override
	protected void doSendMessage(String jsS) {
		this.socket.send(jsS);
	}


}
