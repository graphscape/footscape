/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 9, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client;

import tigase.jaxmpp.core.client.BareJID;
import tigase.jaxmpp.core.client.SessionObject;
import tigase.jaxmpp.core.client.UserProperties;
import tigase.jaxmpp.core.client.connector.AbstractBoshConnector;
import tigase.jaxmpp.core.client.exceptions.JaxmppException;
import tigase.jaxmpp.core.client.xml.Element;
import tigase.jaxmpp.gwt.client.Jaxmpp;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.core.api.gwt.client.SessionConfig;
import com.fs.uixmpp.core.api.gwt.client.StanzaI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;

/**
 * @author wu
 * 
 */
public class XmppControl extends ControlSupport implements XmppControlI {

	protected Jaxmpp jaxmpp;

	protected EventConvertListener ecListener;

	protected SessionConfig config;

	protected ConnectKeeper keeper;

	public XmppControl(String name) {
		super(name);
		jaxmpp = new Jaxmpp();
		this.ecListener = new EventConvertListener(this);
		this.jaxmpp.addListener(this.ecListener);
		this.keeper = new ConnectKeeper(this);

	}

	@Override
	public XmppModelI getModel() {
		return (XmppModelI) this.model;
	}

	@Override
	protected void doAttach() {
		super.doAttach();

	}

	/*
	 * Oct 23, 2012
	 */
	@Override
	protected void doModel(ModelI cm) {
		//
		super.doModel(cm);
		// listen to the authed event
		SessionModelI sm = this.model.getTopObject().find(SessionModelI.class,
				true);
		sm.addValueHandler(SessionModelI.L_IS_AUTHED,
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						XmppControl.this.onAuthed(e);
					}
				});
	}

	// listener to the client ready event.
	// TODO add state for UiObjectI,add StateNotifierI to process this case.
	//
	private void onAuthed(ModelValueEvent e) {
		
		if (this.config != null) {// config already filled by session.
			return;
		}


		SessionModelI sm = (SessionModelI) e.getModel();
		if (sm.isAnonymous()) {
			return;// anonymous user has no xmpp account?
		}
		
		SessionConfig sc = new SessionConfig();
		
		sc.setProperty(SessionConfig.K_DOMAIN, sm.getDomain());// TODO more
																// infor in
																// session
																// model.
		sc.setProperty(SessionConfig.K_RESOURCE, "testing");
		sc.setProperty(SessionConfig.K_URL, "/http-bind");
		sc.setProperty(SessionConfig.K_USER, sm.getXmppUser());// TODO auth
																// success
		// from ClientI.
		sc.setProperty(SessionConfig.K_PASS, sm.getXmppPassword());//

		this.getModel().setValue(XmppModelI.L_JID, sc.getFullJid());// TODO
																	// remove
																	// config.
		this.config = sc;
		this.active();//
	}

	/**
	 * This is called after authed. and may called by ConnectKeeper.
	 */
	@Override
	public void active() {
		UserProperties up = jaxmpp.getProperties();
		up.setUserProperty(SessionObject.SERVER_NAME, this.config.getDomain());
		up.setUserProperty(SessionObject.RESOURCE, this.config.getResource());

		up.setUserProperty(AbstractBoshConnector.BOSH_SERVICE_URL_KEY,
				this.config.getUrl());

		// up.setUserProperty(SessionObject.USER_JID,
		// JID.jidInstance(this.config.getUserJid().toString()));
		String bjid = this.config.getBareJidAsString();
		up.setUserProperty(SessionObject.USER_BARE_JID,
				BareJID.bareJIDInstance(bjid));
		up.setUserProperty(SessionObject.PASSWORD, this.config.getPassword());//
		// see AbstractBoshConnector
		// this.jaxmpp.getSessionObject().setProperty("BOSH#DEFAULT_TIMEOUT_KEY",
		// 5);//

		// println("Loging in...")
		try {
			jaxmpp.login();
		} catch (JaxmppException e) {
			throw new UiException("", e);
		}

	}

	public EventConverterManagerI getECManager() {
		return this.ecListener;
	}

	@Override
	public void doDetach() {
		try {
			this.jaxmpp.disconnect();
		} catch (JaxmppException e) {
			throw new UiException("", e);
		}
		super.doDetach();
	}

	public void send(StanzaImpl si) {
		Element ele = si.getElement();
		try {
			this.jaxmpp.getWriter().write(ele);
		} catch (JaxmppException e) {
			throw new UiException("", e);
		}
	}

	@Override
	public StanzaI createStanza(String name) {
		return new StanzaImpl(name, this);
	}

	@Override
	public StanzaI createStanza(String name, StanzaI.Type type) {

		return new StanzaImpl(name, type, this);
	}

}
