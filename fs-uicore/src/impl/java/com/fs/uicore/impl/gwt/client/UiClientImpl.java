/**
 * Jun 11, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.fs.uicore.api.gwt.client.event.ClientConnectLostEvent;
import com.fs.uicore.api.gwt.client.event.ClientStartFailureEvent;
import com.fs.uicore.api.gwt.client.event.EndpointCloseEvent;
import com.fs.uicore.api.gwt.client.event.EndpointErrorEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtClosingHandler;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.state.State;
import com.fs.uicore.api.gwt.client.support.ContainerAwareUiObjectSupport;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.fs.uicore.api.gwt.client.support.MessageDispatcherImpl;
import com.fs.uicore.impl.gwt.client.endpoint.CometPPs;
import com.fs.uicore.impl.gwt.client.endpoint.CometPPs.ProtocolPort;
import com.fs.uicore.impl.gwt.client.endpoint.EndpointImpl;
import com.fs.uicore.impl.gwt.client.factory.JsonCodecFactoryC;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;

/**
 * @author wu TOTO rename to UiCoreI and impl.
 */
public class UiClientImpl extends ContainerAwareUiObjectSupport implements UiClientI {

	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(UiClientImpl.class);
	
	private String clientId;

	private CodecI.FactoryI cf;

	private RootI root;

	private UiPropertiesI<String> parameters;

	private UiPropertiesI<String> localized;

	private List<Address> uriList;

	private Map<Integer, EndPointI> tryedEndpointMap = new HashMap<Integer, EndPointI>();

	public static final State UNKNOWN = State.valueOf("UNKNOWN");

	//starting 
	public static final State STARTING = State.valueOf("STARTING");

	//failed to connect any endpoint protocol.
	public static final State FAILED = State.valueOf("FAILED");

	//ok state 
	public static final State STARTED = State.valueOf("STARTED");

	//connection is closed,then set to lost state.
	public static final State LOST = State.valueOf("LOST");
	
	private static final String PK_TRYING_INDEX = "_trying_idx";

	/**
	 * Note:only set after client start.
	 */
	private EndPointI endpoint;

	public UiClientImpl(ContainerI c, RootI root) {
		super(c);

		this.root = root;
		this.parameters = new MapProperties<String>();
		this.localized = new MapProperties<String>();

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

	protected EndPointI newEndpoint(int tryIdx) {
		MessageDispatcherI md = new MessageDispatcherImpl("endpoint");
		Address uri = this.uriList.get(tryIdx);
		final EndPointI rt = new EndpointImpl(this.container, uri, md);
		rt.setProperty(PK_TRYING_INDEX, tryIdx);// the trying idx.

		rt.addHandler(EndpointOpenEvent.TYPE, new EventHandlerI<EndpointOpenEvent>() {

			@Override
			public void handle(EndpointOpenEvent t) {
				UiClientImpl.this.onEndpointOpen(t.getEndPoint());
			}
		});
		rt.addHandler(EndpointErrorEvent.TYPE, new EventHandlerI<EndpointErrorEvent>() {

			@Override
			public void handle(EndpointErrorEvent t) {
				UiClientImpl.this.onEndpointError(t.getEndPoint());
			}
		});
		rt.addHandler(EndpointCloseEvent.TYPE, new EventHandlerI<EndpointCloseEvent>() {

			@Override
			public void handle(EndpointCloseEvent t) {
				UiClientImpl.this.onEndpointClose(t);
			}
		});

		rt.addHandler(Path.valueOf("/endpoint/message/client/init/success"),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						UiClientImpl.this.onInitSuccess(rt, t);
					}
				});
		return rt;
	}

	@Override
	public void start() {
		if (!this.isState(UNKNOWN)) {
			throw new UiException("state should be:" + UNKNOWN + ",but state is:" + this.state);
		}
		
		this.setState(STARTING);

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
	
	public void setState(State s){
		State old = this.state;
		super.setState(s);
		LOG.info("state changed,old:" + old + ",new:" + this.state);
	}

	public boolean tryConnect(int uriIdx) {

		EndPointI ep = this.tryedEndpointMap.get(uriIdx);
		if (ep != null) {// tryed before.
			return true;//
		}

		if (uriIdx < this.uriList.size()) {
			// try the new endpoint.
			ep = this.newEndpoint(uriIdx);
			this.tryedEndpointMap.put(uriIdx, ep);
			ep.open();
			return true;
		}

		// failed
		this.setState(FAILED);// all protocols is failed, how to do? only
								// failed,notify user.
		new ClientStartFailureEvent(this).dispatch();
		// see the on error/onclose event processing methods
		return false;
	}

	/**
	 * Jan 1, 2013
	 */
	protected void onInitSuccess(EndPointI ep, MsgWrapper evt) {
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
		//
		this.endpoint = ep;
		// event
		this.setState(STARTED);// started is here.
		new AfterClientStartEvent(this).dispatch();
	}


	public void onEndpointError(EndPointI ep) {

		if (this.isState(STARTED)) {//
			return;// ignore ,because it may a applevel error.
		}

		// ws error will delay the ajax request for that the limit is 2
		// connections.

		if (ep.isOpen()) {
			// this endpoint is open,but error before client/server is ready, so
			// we
			// cannot relay on this endpoint, we close it.
			ep.close();//
		}

		int uriIdx = (Integer) ep.getProperty(PK_TRYING_INDEX, true);
		// close event may not raise for some error?
		this.tryConnect(uriIdx + 1);
	}

	public void onEndpointClose(EndpointCloseEvent evt) {
		
		
		//
		if (this.isState(STARTED)) {//if started,not try other method for connect
			this.setState(LOST);
			new ClientConnectLostEvent(this).dispatch();
			return;// ignore ,because it may a applevel error.
		}

		// ws error will delay the ajax request for that the limit is 2
		// connections.
		EndPointI ep = evt.getEndPoint();
		if (ep.isOpen()) {
			// this endpoint is open,but error before client/server is ready, so
			// we
			// cannot relay on this endpoint, we close it.
			ep.close();//
		}

		int uriIdx = (Integer) ep.getProperty(PK_TRYING_INDEX, true);
		// close event may not raise for some error?
		this.tryConnect(uriIdx + 1);
	}

	/**
	 * open does not gurantee the endpoint is the final one.after client start
	 * is gurantee this. May 12, 2013
	 */
	public void onEndpointOpen(EndPointI ep) {

		MsgWrapper req = new MsgWrapper(Path.valueOf("/client/init"));
		String locale = this.getPreferedLocale();

		req.setPayload("preferedLocale", (locale));

		// int uriIdx = (Integer) ep.getProperty(PK_TRYING_INDEX, true);

		ep.sendMessage(req);

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
	public EndPointI getEndpoint(boolean force) {
		//
		if (this.endpoint == null && force) {
			throw new UiException("end point is null, client not started?");
		}
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
