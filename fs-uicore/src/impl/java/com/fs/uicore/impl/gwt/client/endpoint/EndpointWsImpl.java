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
import com.fs.uicore.api.gwt.client.event.ClientClosingEvent;
import com.fs.uicore.api.gwt.client.html5.WebSocketJSO;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.support.EndpointSupport;
import com.fs.uicore.impl.gwt.client.comet.GometI;
import com.fs.uicore.impl.gwt.client.comet.ws.AjaxGomet;
import com.fs.uicore.impl.gwt.client.comet.ws.WsGomet;
import com.fs.uicore.impl.gwt.client.endpoint.WsProtocolAndPorts.ProtocolPort;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class EndpointWsImpl extends EndpointSupport {

	public static interface ProtocolI {
		public GometI open(boolean force);
	}

	public static class WsProtocol implements ProtocolI {

		/*
		 * May 9, 2013
		 */
		@Override
		public GometI open(boolean force) {
			//
			String uri = this.resolveWsUrl();
			WebSocketJSO wso = WebSocketJSO.newInstance(uri, false);
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

		protected String resolveWsUrl() {
			String host = Window.Location.getHostName();
			ProtocolPort pp = WsProtocolAndPorts.getInstance().getFirstOrDefault();

			String rt = pp.protocol + "://" + host + ":" + pp.port + "/wsa/default";

			return rt;
		}

	}

	public static class AjaxProtocol implements ProtocolI {

		/*
		 * May 9, 2013
		 */
		@Override
		public GometI open(boolean force) {
			//
			String uri = this.resolveWsUrl();

			AjaxGomet rt = new AjaxGomet(uri);
			rt.open();
			return rt;

		}

		protected String resolveWsUrl() {
			String host = Window.Location.getHostName();
			String port = Window.Location.getPort();

			String rt = "http://" + host + ":" + port + "/aja/default";

			return rt;
		}

	}

	private GometI socket;

	private String uri;

	private Map<String, ProtocolI> protocols;

	/**
	 * @param md
	 */
	public EndpointWsImpl(ContainerI c, String protocol, MessageDispatcherI md) {
		super(c, protocol, md, new MessageCacheImpl(c));
		this.protocols = new HashMap<String, ProtocolI>();
		this.protocols.put("websocket", new WsProtocol());
		this.protocols.put("ajax", new AjaxProtocol());

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
	public void open() {
		super.open();
		ProtocolI pro = this.protocols.get(this.protocol);

		this.socket = pro.open(false);
		if (this.socket == null) {
			Window.alert("protocol is not support:" + this.protocol);
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

	@Override
	public String getUri() {
		return this.uri;
	}

}
