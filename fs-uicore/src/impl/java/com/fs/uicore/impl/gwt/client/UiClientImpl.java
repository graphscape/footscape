/**
 * Jun 11, 2012
 */
package com.fs.uicore.impl.gwt.client;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.CodecI.FactoryI;
import com.fs.uicore.api.gwt.client.ModelI;
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
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.support.ContainerAwareUiObjectSupport;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.fs.uicore.impl.gwt.client.endpoint.EndpointWsImpl;
import com.fs.uicore.impl.gwt.client.factory.JsonCodecFactoryC;
import com.fs.uicore.impl.gwt.client.message.MessageDispatcherFactory;

/**
 * @author wu TOTO rename to UiCoreI and impl.
 */
public class UiClientImpl extends ContainerAwareUiObjectSupport implements UiClientI {

	private String clientId;

	private CodecI.FactoryI cf;

	private RootI root;

	private UiPropertiesI<String> parameters;

	private MessageDispatcherI.FactoryI factory;

	private EndPointI endpoint;

	public UiClientImpl(RootI root) {
		this.root = root;
		this.parameters = new MapProperties<String>();
		this.factory = new MessageDispatcherFactory();
		this.factory.parent(this);
		MessageDispatcherI md = this.factory.get("endpoint");
		this.endpoint = new EndpointWsImpl(md);
		this.endpoint.parent(this);
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
		this.endpoint.getMessageDispatcher().addHandler(Path.valueOf("/client/init/success"),
				new MessageHandlerI() {

					@Override
					public void handle(EndpointMessageEvent t) {
						UiClientImpl.this.onInitSuccess(t);
					}
				});
		this.endpoint.open();

	}

	/**
	 * Jan 1, 2013
	 */
	protected void onInitSuccess(EndpointMessageEvent evt) {
		MessageData t = evt.getMessage();
		String sd = (String) t.getPayloads().getProperty("clientId", true);
		String sid = sd;
		if (sid == null) {
			throw new UiException("got a null sessionId");
		}
		this.clientId = sd;
		ObjectPropertiesData opd = (ObjectPropertiesData) t.getPayload("parameters", true);//
		// parameters:

		for (String key : opd.keyList()) {
			String valueS = (String) opd.getProperty(key);

			this.parameters.setProperty(key, valueS);

		}

		new AfterClientStartEvent(this).dispatch();
	}

	public void onEndpointOpen() {

		MsgWrapper req = new MsgWrapper(Path.valueOf("/client/init"));
		String locale = this.getPreferedLocale();

		req.setPayload("preferedLocale", (locale));
		this.endpoint.sendMessage(req);

	}

	protected String getPreferedLocale() {
		return null;//
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
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.UiClientI#getRootModel()
	 */
	@Override
	public ModelI getRootModel() {
		// TODO Auto-generated method stub
		return this.container.get(ModelI.class, false);//
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

}
