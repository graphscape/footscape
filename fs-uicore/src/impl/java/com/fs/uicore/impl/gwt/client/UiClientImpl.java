/**
 * Jun 11, 2012
 */
package com.fs.uicore.impl.gwt.client;

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
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.ClientClosingEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtClosingHandler;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.support.ContainerAwareUiObjectSupport;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.fs.uicore.api.gwt.client.support.MessageDispatcherImpl;
import com.fs.uicore.api.gwt.client.window.UiWindow;
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

	public UiClientImpl(ContainerI c, RootI root) {
		super(c);
		this.root = root;
		this.parameters = new MapProperties<String>();
		this.localized = new MapProperties<String>();
		MessageDispatcherI md = new MessageDispatcherImpl("endpoint");
		this.endpoint = new EndpointWsImpl(c, md);
		this.endpoint.parent(this);

		String wsP = UiWindow.getParameter("fs.wsPort", null);
		String wssP = UiWindow.getParameter("fs.wssPort", null);
		if (wsP != null) {
			this.setParameter(UiClientI.RK_WS_PORT, wsP);
		}

		if (wssP != null) {
			this.setParameter(UiClientI.RK_WSS_PORT, wssP);
		}
		Window.addWindowClosingHandler(new GwtClosingHandler() {

			@Override
			protected void handleInternal(ClosingEvent evt) {
				//
				new ClientClosingEvent(UiClientImpl.this).dispatch();
			}
		});
	}

	@Override
	public void doAttach() {

		// TODO move to SPI.active.
		this.cf = new JsonCodecFactoryC();

	}

	@Override
	public void start() {
		this.endpoint.addHandler(EndpointOpenEvent.TYPE, new EventHandlerI<EndpointOpenEvent>() {

			@Override
			public void handle(EndpointOpenEvent t) {
				UiClientImpl.this.onEndpointOpen();
			}
		});
		this.endpoint.addHandler(Path.valueOf("/endpoint/message/client/init/success"),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						UiClientImpl.this.onInitSuccess(t);
					}
				});
		this.endpoint.open();

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

		new AfterClientStartEvent(this).dispatch();
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
