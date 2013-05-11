/**
 * Jun 11, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.CodecI.FactoryI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.endpoint.Address;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.ClientClosingEvent;
import com.fs.uicore.api.gwt.client.event.ClientStartFailureEvent;
import com.fs.uicore.api.gwt.client.event.EndpointCloseEvent;
import com.fs.uicore.api.gwt.client.event.EndpointErrorEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtClosingHandler;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.state.State;
import com.fs.uicore.api.gwt.client.support.ContainerAwareUiObjectSupport;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.fs.uicore.api.gwt.client.support.MessageDispatcherImpl;
import com.fs.uicore.impl.gwt.client.endpoint.CometPPs;
import com.fs.uicore.impl.gwt.client.endpoint.CometPPs.ProtocolPort;
import com.fs.uicore.impl.gwt.client.endpoint.EndpointWsImpl;
import com.fs.uicore.impl.gwt.client.factory.JsonCodecFactoryC;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;

/**
 * @author wu TOTO rename to UiCoreI and impl.
 */
public class UiClientImpl extends ContainerAwareUiObjectSupport implements UiClientI {

	private String clientId;

	private CodecI.FactoryI cf;

	private RootI root;

	private UiPropertiesI<String> parameters;

	private EndPointI endpoint;

	private UiPropertiesI<String> localized;

	private List<Address> uriList;

	private int tryingUriIdx = -1;
	
	private Set<Integer> tryedIndex = new HashSet<Integer>();

	public static final State UNKNOWN = State.valueOf("UNKNOWN");

	public static final State STARTING = State.valueOf("STARTING");

	public static final State FAILED = State.valueOf("FAILED");

	public static final State STARTED = State.valueOf("STARTED");

	public UiClientImpl(ContainerI c, RootI root) {
		super(c);

		this.root = root;
		this.parameters = new MapProperties<String>();
		this.localized = new MapProperties<String>();
		MessageDispatcherI md = new MessageDispatcherImpl("endpoint");
		this.endpoint = new EndpointWsImpl(c, md);
		this.endpoint.parent(this);

		Window.addWindowClosingHandler(new GwtClosingHandler() {

			@Override
			protected void handleInternal(ClosingEvent evt) {
				//
				new ClientClosingEvent(UiClientImpl.this).dispatch();
			}
		});
		this.setState(UNKNOWN);
	}

	private int getWindowLocationPort() {
		String portS = Window.Location.getPort();
		int port = Integer.parseInt(portS);
		return port;
	}

	private String getWindowLocationProtocol() {
		String pro = Window.Location.getProtocol();

		if (pro.endsWith(":")) {
			pro = pro.substring(0, pro.length() - 1);
		}
		return pro;
	}

	@Override
	public void doAttach() {

		// TODO move to SPI.active.
		this.cf = new JsonCodecFactoryC();

	}

	@Override
	public void start() {
		if (!this.isState(UNKNOWN)) {
			throw new UiException("state should be:" + UNKNOWN + ",but state is:" + this.state);
		}
		this.setState(STARTING);

		this.endpoint.addHandler(EndpointOpenEvent.TYPE, new EventHandlerI<EndpointOpenEvent>() {

			@Override
			public void handle(EndpointOpenEvent t) {
				UiClientImpl.this.onEndpointOpen();
			}
		});
		this.endpoint.addHandler(EndpointErrorEvent.TYPE, new EventHandlerI<EndpointErrorEvent>() {

			@Override
			public void handle(EndpointErrorEvent t) {
				UiClientImpl.this.onEndpointError();
			}
		});
		this.endpoint.addHandler(EndpointCloseEvent.TYPE, new EventHandlerI<EndpointCloseEvent>() {

			@Override
			public void handle(EndpointCloseEvent t) {
				UiClientImpl.this.onEndpointClose();
			}
		});

		this.endpoint.addHandler(Path.valueOf("/endpoint/message/client/init/success"),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						UiClientImpl.this.onInitSuccess(t);
					}
				});
		this.uriList = new ArrayList<Address>();
		List<ProtocolPort> ppL = new ArrayList<ProtocolPort>(CometPPs.getInstance().getConfiguredList());

		if (ppL.isEmpty()) {// not configured,then default ones

			{// try ws/wss first,
				String hpro = getWindowLocationProtocol();
				boolean https = hpro.equals("https");
				String wsp = https ? "wss" : "ws";
				String portS = Window.Location.getPort();
				int port = Integer.parseInt(portS);
				ppL.add(new ProtocolPort(wsp, port));
			}
			{// try ajax second
				String pro = getWindowLocationProtocol();
				int port = getWindowLocationPort();
				ppL.add(new ProtocolPort(pro, port));
			}

		}

		String host = Window.Location.getHostName();
		String ajaRes = "/aja/default";
		String wsRes = "/wsa/default";

		for (ProtocolPort pp : ppL) {
			String pro = pp.protocol;
			String resource;
			int port = pp.port;

			if (pro.startsWith("http")) {
				resource = ajaRes;
			} else if (pro.startsWith("ws")) {
				resource = wsRes;
			} else {
				throw new UiException("not supported pro:" + pro);
			}
			Address uri = new Address(pro, host, port, resource);
			this.uriList.add(uri);
		}

		this.tryConnect(0);
	}

	public boolean tryConnect(int uriIdx) {
		if(this.tryedIndex.contains(uriIdx)){
			return true;//
		}		
		this.tryedIndex.add(uriIdx);
		
		if (this.tryingUriIdx == uriIdx) {
			// is already in trying this uri
			// LOG.warn("");
			return true;// ignore
		}

		this.tryingUriIdx = uriIdx;

		if (uriIdx < this.uriList.size()) {

			Address uri = this.uriList.get(uriIdx);
			this.endpoint.open(uri);
			return true;
		}
		
		//failed 
		this.setState(FAILED);// all protocols is failed, how to do? only
								// failed,notify user.
		new ClientStartFailureEvent(this).dispatch();
		// see the on error/onclose event processing methods
		return false;
	}

	/**
	 * Jan 1, 2013
	 */
	protected void onInitSuccess(MsgWrapper evt) {
		MessageData t = evt.getMessage();
		String sd = (String) t.getPayloads().getProperty("clientId", true);
		String sid = sd;
		if (sid == null) {
			throw new UiException("got a null sessionId");
		}
		this.clientId = sd;
		{
			ObjectPropertiesData opd = (ObjectPropertiesData) t.getPayload("parameters", true);//
			// parameters:

			for (String key : opd.keyList()) {
				String valueS = (String) opd.getProperty(key);

				this.parameters.setProperty(key, valueS);

			}
		}
		{
			// localized resource
			ObjectPropertiesData opd2 = (ObjectPropertiesData) t.getPayload("localized", true);//
			// parameters:

			for (String key : opd2.keyList()) {
				String valueS = (String) opd2.getProperty(key);

				this.localized.setProperty(key, valueS);

			}
		}
		// event
		this.setState(STARTED);// started is here.
		new AfterClientStartEvent(this).dispatch();
	}

	private void onEndpointErrorOrClose() {
		if (this.isState(STARTED)) {
			return;// ignore ,because it may a applevel error.
		}
		// close event may not raise for some error?
		this.tryConnect(this.tryingUriIdx + 1);
	}

	public void onEndpointError() {
		this.onEndpointErrorOrClose();
	}

	public void onEndpointClose() {
		this.onEndpointErrorOrClose();
	}

	public void onEndpointOpen() {

		MsgWrapper req = new MsgWrapper(Path.valueOf("/client/init"));
		String locale = this.getPreferedLocale();

		req.setPayload("preferedLocale", (locale));
		this.endpoint.sendMessage(req);

	}

	@Override
	public String getPreferedLocale() {
		return null;//
	}

	@Override
	public String localized(String key) {
		// i18n
		String rt = this.localized.getProperty(key);
		if (rt == null) {
			return key;
		}
		return rt;
	}

	/**
	 * Client got the sessionId from server,client stared on. Nov 14, 2012
	 */

	public String getParameter(String key, String def) {
		return this.parameters.getProperty(key, def);
	}

	public String getParameter(String key, boolean force) {
		return this.parameters.getProperty(key, force);
	}

	protected WidgetFactoryI getWidgetFactory() {
		return this.getContainer().get(WidgetFactoryI.class, true);

	}

	/**
	 * @return the root
	 */
	public RootI getRoot() {
		return root;
	}

	/* */
	@Override
	public void attach() {

		super.attach();
		this.root.attach();// TODO remove this call,add root as a child.

	}

	/* */
	@Override
	public void detach() {
		this.root.detach();//
		super.detach();

	}

	/*
	 * Nov 26, 2012
	 */
	@Override
	public String getClientId() {
		//
		return this.clientId;
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public FactoryI getCodecFactory() {
		//
		return this.cf;
	}

	@Override
	public void setParameter(String key, String value) {
		this.parameters.setProperty(key, value);
	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public EndPointI getEndpoint() {
		//
		return this.endpoint;
	}

	/*
	 * Apr 4, 2013
	 */
	@Override
	public int getParameterAsInt(String key, int def) {
		//
		String rt = this.getParameter(key, false);
		return rt == null ? def : Integer.parseInt(rt);
	}

	/*
	 * Apr 4, 2013
	 */
	@Override
	public boolean getParameterAsBoolean(String key, boolean def) {
		//
		String rt = this.getParameter(key, false);
		return rt == null ? def : Boolean.valueOf(rt);

	}

}
