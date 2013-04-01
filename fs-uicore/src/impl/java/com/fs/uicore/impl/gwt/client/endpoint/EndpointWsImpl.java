/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.impl.gwt.client.endpoint;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.endpoint.MessageCacheI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.event.EndpointCloseEvent;
import com.fs.uicore.api.gwt.client.event.EndpointErrorEvent;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
import com.fs.uicore.api.gwt.client.event.EndpointUnbondEvent;
import com.fs.uicore.api.gwt.client.html5.ErrorJSO;
import com.fs.uicore.api.gwt.client.html5.EventJSO;
import com.fs.uicore.api.gwt.client.html5.ReadyState;
import com.fs.uicore.api.gwt.client.html5.WebSocketJSO;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class EndpointWsImpl extends UiObjectSupport implements EndPointI {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(EndpointWsImpl.class);

	private WebSocketJSO socket;

	private CodecI messageCodec;

	private String uri;

	private boolean serverIsReady;

	private String clientId;

	private String terminalId;

	private UserInfo userInfo;

	private MessageCacheI messageCache;

	/**
	 * @param md
	 */
	public EndpointWsImpl(ContainerI c, MessageDispatcherI md) {
		super(c);
		this.messageCache = new MessageCacheImpl(c);
		this.addHandler(
				EndpointMessageEvent.TYPE.getAsPath().concat(
						Path.valueOf("/control/status/serverIsReady", '/')),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						EndpointWsImpl.this.onServerIsReady(t);
					}
				});
		MessageHandlerI<MsgWrapper> bindingMH = new MessageHandlerI<MsgWrapper>() {

			@Override
			public void handle(MsgWrapper t) {
				EndpointWsImpl.this.onBindingSuccess(t);
			}
		};
		this.addHandler(Path.valueOf("/endpoint/message/terminal/auth/success"), bindingMH);
		this.addHandler(Path.valueOf("/endpoint/message/terminal/binding/success"), bindingMH);

		MessageHandlerI<MsgWrapper> unBindingMH = new MessageHandlerI<MsgWrapper>() {

			@Override
			public void handle(MsgWrapper t) {
				EndpointWsImpl.this.onUnbindingSuccess(t);
			}
		};
		this.addHandler(Path.valueOf("/endpoint/message/terminal/unbinding/success"), unBindingMH);

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
		new EndpointKeeper(this).start();
		this.messageCache.start();
	}

	protected void onServerIsReady(MsgWrapper e) {
		MessageData md = e.getMessage();
		this.clientId = md.getString("clientId", true);
		this.terminalId = md.getString("terminalId", true);
		this.serverIsReady = true;
		new EndpointOpenEvent(this).dispatch();
	}

	@Override
	public void open() {
		UiClientI client = this.getClient(true);//
		String protocol = Window.Location.getProtocol();
		String host = Window.Location.getHostName();
		String port = Window.Location.getPort();
		boolean https = protocol.equals("https:");

		String wsProtocol = https ? "wss" : "ws";
		//
		String portSet = client.getParameter(https ? UiClientI.RK_WSS_PORT : UiClientI.RK_WS_PORT, false);

		port = portSet == null ? (https ? "443" : "80") : portSet;

		this.uri = wsProtocol + "://" + host + ":" + port + "/wsa/default";

		this.messageCodec = this.getClient(true).getCodecFactory().getCodec(MessageData.class);

		this.socket = WebSocketJSO.newInstance(uri, true);
		this.socket.onOpen(new HandlerI<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				//
				EndpointWsImpl.this.onWsOpen(t);
			}
		});
		this.socket.onMessage(new HandlerI<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				//
				EndpointWsImpl.this.onWsMessage(t);

			}
		});
		this.socket.onClose(new HandlerI<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				//
				EndpointWsImpl.this.onWsClose(t);
			}
		});
		this.socket.onError(new HandlerI<ErrorJSO>() {

			@Override
			public void handle(ErrorJSO t) {
				//
				EndpointWsImpl.this.onWsError(t);
			}
		});

	}

	protected void assertSocketOpen(boolean appLevel) {
		if (this.socket == null) {
			throw new UiException("socket is null");

		}

		ReadyState rs = this.socket.getReadyState();
		if (!rs.isOpen()) {
			throw new UiException("socket is not open,readyState:" + rs);
		}

		if (appLevel && !this.serverIsReady) {
			throw new UiException("server is not ready");
		}

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void sendMessage(MessageData req) {
		this.assertSocketOpen(true);//
		if (this.userInfo != null) {
			req.setHeader("sessionId", this.getSessionId());//
		}

		req.setHeader("_resonse_address", "tid://" + this.terminalId);

		this.sendMessageDirect(req);

	}

	private void sendMessageDirect(MessageData req) {
		//
		JSONValue js = (JSONValue) this.messageCodec.encode(req);
		String jsS = js.toString();
		this.messageCache.addMessage(req);// for later reference
		this.socket.send(jsS);

	}

	protected void onWsOpen(Object evt) {
		// wait server is ready
		LOG.info("ws open, send client is ready to server,and wait server is ready.");
		MessageData req = new MessageData("/control/status/clientIsReady");
		this.sendMessageDirect(req);

	}

	protected void onWsClose(EventJSO evt) {
		LOG.info("onWsClose,evt:" + evt.toLongString());
		this.serverIsReady = false;
		this.clientId = null;
		this.terminalId = null;//
		new EndpointCloseEvent(this).dispatch();

	}

	protected void onWsError(ErrorJSO er) {
		String errorS = er.toLongString();
		LOG.error("onWsError,errorJSO:" + er.toLongString(), null);
		new EndpointErrorEvent(this, errorS).dispatch();

	}

	protected void onWsMessage(EventJSO evt) {
		String msg = "" + evt.getData();
		JSONValue jsonV = JSONParser.parseStrict(msg);
		MessageData md = (MessageData) this.messageCodec.decode(jsonV);
		String sid = md.getSourceId();
		if (sid != null) {
			MessageData req = this.messageCache.getMessage(sid);
			if (req == null) {
				LOG.info("request not found,may timeout or the source message is from other side,message:"
						+ md);
			} else {
				md.setPayload(MessageData.PK_SOURCE, req);
			}
		}
		Path p = md.getPath();
		Path tp = EndpointMessageEvent.TYPE.getAsPath();

		md.setHeader(MessageData.HK_PATH, tp.concat(p).toString());
		new EndpointMessageEvent(this, md).dispatch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.endpoint.EndPointI#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.serverIsReady;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.endpoint.EndPointI#getUri()
	 */
	@Override
	public String getUri() {
		return this.uri;
	}

	@Override
	public void auth(PropertiesData<Object> pts) {
		MessageData req = new MessageData("/terminal/auth");
		req.setPayloads(pts);
		this.sendMessage(req);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public boolean isBond() {
		//
		return this.userInfo != null;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public String getSessionId() {
		//
		return this.userInfo.getString("sessionId", true);
	}

	/**
	 * Dec 23, 2012
	 */
	public void onBindingSuccess(MsgWrapper evt) {
		MessageData md = evt.getTarget();
		System.out.println("onBindingSuccess:" + md);
		this.userInfo = new UserInfo();
		String sid = md.getString("sessionId", true);
		this.userInfo.setProperties(md.getPayloads());

		new EndpointBondEvent(this, this.getSessionId()).dispatch();
	}

	public void onUnbindingSuccess(MsgWrapper evt) {
		this.userInfo = null;
		new EndpointUnbondEvent(this).dispatch();
	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public void sendMessage(MsgWrapper req) {
		this.sendMessage(req.getTarget());//
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void logout() {
		//
		if (!this.isBond()) {
			throw new UiException("not bound yet.");
		}

		MessageData req = new MessageData("/terminal/unbinding");
		req.setPayload("sessionId", this.getSessionId());

		this.sendMessage(req);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public UserInfo getUserInfo() {
		//
		return this.userInfo;
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public MessageCacheI getRequestCache() {
		//
		return null;
	}

}
