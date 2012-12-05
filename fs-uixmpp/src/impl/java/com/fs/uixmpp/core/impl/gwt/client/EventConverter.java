/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client;

import tigase.jaxmpp.core.client.observer.BaseEvent;
import tigase.jaxmpp.core.client.observer.EventType;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class EventConverter<F extends BaseEvent, T extends Event> {

	public static class Context<F extends BaseEvent> {

		private F event;

		private XmppControl session;

		public Context(F be, XmppControl s) {
			this.event = be;
			this.session = s;
		}

		/**
		 * @return the event
		 */
		public F getEvent() {
			return event;
		}

		/**
		 * @return the session
		 */
		public XmppControl getXmpp() {
			return session;
		}

	}

	protected EventType eventType;

	protected String typeName;// for debug

	protected EventConverter(EventType et, String name) {
		this.eventType = et;
		typeName = name;
	}

	/**
	 * @return the eventType
	 */
	public EventType getEventType() {
		return eventType;
	}

	public String getTypeName() {
		return typeName;
	}

	public abstract T convert(Context<F> ec);

}
