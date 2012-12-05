/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 22, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tigase.jaxmpp.core.client.Connector;
import tigase.jaxmpp.core.client.Connector.ConnectorEvent;
import tigase.jaxmpp.core.client.JaxmppCore;
import tigase.jaxmpp.core.client.JaxmppCore.JaxmppEvent;
import tigase.jaxmpp.core.client.exceptions.JaxmppException;
import tigase.jaxmpp.core.client.observer.BaseEvent;
import tigase.jaxmpp.core.client.observer.EventType;
import tigase.jaxmpp.core.client.observer.Listener;
import tigase.jaxmpp.core.client.xmpp.modules.auth.AuthModule;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.fs.uixmpp.core.impl.gwt.client.ec.AuthSuccessEC;
import com.fs.uixmpp.core.impl.gwt.client.ec.ConnectedEC;
import com.fs.uixmpp.core.impl.gwt.client.ec.DisconnectedEC;
import com.fs.uixmpp.core.impl.gwt.client.ec.ResourceBindSuccessEC;
import com.fs.uixmpp.core.impl.gwt.client.ec.StanzaReceivedEC;
import com.fs.uixmpp.core.impl.gwt.client.ec.StateChangeEC;
import com.fs.uixmpp.core.impl.gwt.client.ec.UnknownEC;

/**
 * @author wu
 * 
 */
public class EventConvertListener implements Listener, EventConverterManagerI {

	private static UiLoggerI LOG = UiLoggerFactory
			.getLogger(EventConvertListener.class);

	protected Map<EventType, EventConverter> eventConverterMap;

	protected EventConverter defaultEc;

	protected XmppControl xmppSessionImpl;

	protected boolean debug = true;

	public EventConvertListener(XmppControl xs) {
		this.xmppSessionImpl = xs;

		this.defaultEc = new UnknownEC(null, "Unknown");

		eventConverterMap = new HashMap<EventType, EventConverter>();
		//
		this.addEventConverter(new StateChangeEC());
		this.addEventConverter(new ConnectedEC());// Connected.
		this.addEventConverter(new UnknownEC(Connector.BodyReceived,
				"BodyReceived"));
		this.addEventConverter(new StanzaReceivedEC());
		this.addEventConverter(new UnknownEC(Connector.EncryptionEstablished,
				"EncryptionEstablished"));
		this.addEventConverter(new UnknownEC(Connector.Error, "Error"));
		this.addEventConverter(new UnknownEC(Connector.StanzaSending,
				"StanzaSending"));
		this.addEventConverter(new UnknownEC(Connector.StreamTerminated,
				"StreamTerminated"));
		this.addEventConverter(new UnknownEC(AuthModule.AuthFailed,
				"AuthFailed"));
		this.addEventConverter(new UnknownEC(AuthModule.AuthStart, "AuthStart"));
		this.addEventConverter(new AuthSuccessEC());

		this.addEventConverter(new ResourceBindSuccessEC());
		this.addEventConverter(new UnknownEC(JaxmppCore.Connected,
				"JaxmppCore.Connected"));
		this.addEventConverter(new DisconnectedEC());

	}

	@Override
	public void addEventConverter(EventConverter ec) {
		EventConverter old = this.eventConverterMap.put(ec.getEventType(), ec);
		if (old != null) {
			throw new UiException("TODO allow multiple EC for same type");
		}
	}

	@Override
	public void handleEvent(BaseEvent be) throws JaxmppException {

		this.handleEventInternal(be);// TODO exception process

	}

	protected void handleEventInternal(BaseEvent be) {
		EventType et = be.getType();
		EventConverter ec = this.eventConverterMap.get(et);
		EventConverter.Context<BaseEvent> ctx = new EventConverter.Context<BaseEvent>(
				be, this.xmppSessionImpl);
		String msg = this.toString(be);

		Event e = null;
		if (ec == null) {
			ec = this.defaultEc;//
		}

		LOG.debug("eventTypeName:" + ec.getTypeName() + "," + msg);

		e = ec.convert(ctx);
		this.xmppSessionImpl.dispatch(e);

	}

	protected String toString(BaseEvent be) {
		StringBuffer sb = new StringBuffer();
		if (be instanceof ConnectorEvent) {
			ConnectorEvent ce = (ConnectorEvent) be;
			sb.append("ConnectorEvent:");
			sb.append("caught:");
			sb.append(ce.getCaught());
			sb.append(",stanza:");
			sb.append(ce.getStanza());
			sb.append(",streamError:");
			sb.append(ce.getStreamError());
			sb.append(",streamErrorElement:");
			sb.append(ce.getStreamErrorElement());
			sb.append(",type:");
			sb.append(ce.getType());

		} else if (be instanceof JaxmppEvent) {
			JaxmppEvent ce = (JaxmppEvent) be;
			sb.append("ConnectorEvent:");
			sb.append("caught:");
			sb.append(ce.getCaught());
			sb.append(",type:");
			sb.append(ce.getType());
		} else {
			sb.append("other:");
			sb.append(be);
		}
		// sb.append(e);
		return sb.toString();

	}
}
