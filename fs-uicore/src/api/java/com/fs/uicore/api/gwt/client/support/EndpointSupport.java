/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.Console;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.Address;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.endpoint.MessageCacheI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.ClientClosingEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBusyEvent;
import com.fs.uicore.api.gwt.client.event.EndpointCloseEvent;
import com.fs.uicore.api.gwt.client.event.EndpointErrorEvent;
import com.fs.uicore.api.gwt.client.event.EndpointFreeEvent;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
import com.fs.uicore.api.gwt.client.event.EndpointUnbondEvent;
import com.fs.uicore.api.gwt.client.event.StateChangeEvent;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
/**
 * @author wu
 * 
 */
public abstract class EndpointSupport extends UiObjectSupport implements EndPointI {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(EndpointSupport.class);

	private CodecI messageCodec;

	private boolean serverIsReady;

	private String clientId;

	private String terminalId;

	private UserInfo userInfo;

	private MessageCacheI messageCache;

	private EndpointFreeEvent lastFreeEvent;

	private EndpointBusyEvent lastBusyEvent;

	private Console console = Console.getInstance();

	protected Address uri;

	protected String protocol;

	/**
	 * @param md
	 */
	public EndpointSupport(ContainerI c, Address uri, MessageDispatcherI md, MessageCacheI mc) {
		super(c);
		this.uri = uri;
		this.protocol = uri.getProtocol();
		this.messageCache = mc;
		this.messageCache.addHandler(new EventHandlerI<StateChangeEvent>() {

			@Override
			public void handle(StateChangeEvent t) {
				EndpointSupport.this.onMessageCacheUpdate(t);
			}
		});
		this.addHandler(
				EndpointMessageEvent.TYPE.getAsPath().concat(
						Path.valueOf("/control/status/serverIsReady", '/')),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						EndpointSupport.this.onServerIsReady(t);
					}
				});
		MessageHandlerI<MsgWrapper> bindingMH = new MessageHandlerI<MsgWrapper>() {

			@Override
			public void handle(MsgWrapper t) {
				EndpointSupport.this.onBindingSuccess(t);
			}
		};
		// TODO move to SPI active method.
		this.addHandler(Path.valueOf("/endpoint/message/terminal/auth/success"), bindingMH);
		this.addHandler(Path.valueOf("/endpoint/message/terminal/binding/success"), bindingMH);

		MessageHandlerI<MsgWrapper> unBindingMH = new MessageHandlerI<MsgWrapper>() {

			@Override
			public void handle(MsgWrapper t) {
				EndpointSupport.this.onUnbindingSuccess(t);
			}
		};
		this.addHandler(Path.valueOf("/endpoint/message/terminal/unbinding/success"), unBindingMH);

	}

	/**
	 * Apr 4, 2013
	 */
	protected void onMessageCacheUpdate(StateChangeEvent t) {

		if (this.messageCache.size() == 0) {
			this.lastBusyEvent = null;
			if (this.lastFreeEvent == null) {
				this.lastFreeEvent = new EndpointFreeEvent(this);
				this.lastFreeEvent.dispatch();
			}
			// else ignore
		} else {

			this.lastFreeEvent = null;
			if (this.lastBusyEvent == null) {
				this.lastBusyEvent = new EndpointBusyEvent(this);
				this.lastBusyEvent.dispatch();
			}
		}
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

		this.messageCache.start();
		this.getClient(true).addHandler(ClientClosingEvent.TYPE, new EventHandlerI<ClientClosingEvent>() {

			@Override
			public void handle(ClientClosingEvent t) {
				EndpointSupport.this.onClientClosing(t);
			}
		});
	}

	/**
	 * Apr 4, 2013
	 */
	protected void onClientClosing(ClientClosingEvent t) {
		this.close();
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
		if (this.messageCodec == null) {
			this.messageCodec = this.getClient(true).getCodecFactory().getCodec(MessageData.class);
		}

	}

	protected void assertIsReady() {

		if (!this.isReady()) {
			throw new UiException(getShortName() + ",server is not ready");
		}

	}

	protected boolean isReady() {
		return this.isNativeSocketOpen() && this.serverIsReady;
	}

	protected abstract boolean isNativeSocketOpen();

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void sendMessage(MessageData req) {
		//applevel message sending.
		this.assertIsReady();

		if (this.userInfo != null) {
			req.setHeader("sessionId", this.getSessionId());//
		}

		req.setHeader("_resonse_address", "tid://" + this.terminalId);

		this.sendMessageDirect(req);

	}

	private void sendMessageDirect(final MessageData req) {
		//
		JSONValue js = (JSONValue) this.messageCodec.encode(req);
		String jsS = js.toString();
		this.messageCache.addMessage(req);// for later reference
		this.doSendMessage(jsS, new HandlerI<String>() {

			@Override
			public void handle(String t) {
				EndpointSupport.this.onSendFailure(req);
			}
		});
	}

	/**
	 * @param req
	 */
	protected void onSendFailure(MessageData req) {
		this.messageCache.removeMessage(req.getId());//
	}

	protected abstract void doSendMessage(String msg, HandlerI<String> onfailure);

	/**
	 * Called after the underlying protocol(comet) is established.
	 */
	protected void onConnected() {
		// wait server is ready
		LOG.info(getShortName() + " is open, waiting server is ready.");
		MessageData req = new MessageData("/control/status/clientIsReady");
		this.sendMessageDirect(req);

	}

	protected void onClosed(String code, String reason) {
		this.serverIsReady = false;
		this.clientId = null;
		this.terminalId = null;//
		new EndpointCloseEvent(this, code, reason).dispatch();

	}

	protected void onError(String msg) {
		LOG.error(getShortName() + ",error msg:" + msg, null);
		new EndpointErrorEvent(this, msg).dispatch();

	}

	protected void onMessage(String msg) {
		JSONValue jsonV = JSONParser.parseStrict(msg);
		MessageData md = (MessageData) this.messageCodec.decode(jsonV);
		String sid = md.getSourceId();
		if (sid != null) {
			MessageData req = this.messageCache.removeMessage(sid);
			if (req == null) {
				LOG.info(getShortName()
						+ ",request not found,may timeout or the source message is from other side,message:"
						+ md);
			} else {
				md.setPayload(MessageData.PK_SOURCE, req);
			}
		}
		Path p = md.getPath();
		Path tp = EndpointMessageEvent.TYPE.getAsPath();
		ErrorInfosData eis = md.getErrorInfos();
		if (eis.hasError()) {
			this.console.error(eis);
		}
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
		LOG.info(getShortName() + ",onBindingSuccess:" + md);
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

	private String getShortName() {
		return "endpoint(" + this.uri + ")";
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void logout() {
		//
		if (!this.isBond()) {
			throw new UiException(getShortName() + " not bound yet.");
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

	@Override
	public Address getUri() {
		return uri;
	}

}
