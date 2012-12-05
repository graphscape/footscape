/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client.ec;

import tigase.jaxmpp.core.client.observer.BaseEvent;
import tigase.jaxmpp.core.client.observer.EventType;

import com.fs.uixmpp.core.api.gwt.client.event.UnknownEvent;
import com.fs.uixmpp.core.impl.gwt.client.EventConverter;

/**
 * @author wu
 * 
 */
public class UnknownEC extends EventConverter<BaseEvent, UnknownEvent> {

	/**
	 * @param et
	 * @param name
	 */
	public UnknownEC(EventType et, String name) {
		super(et, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uixmpp.impl.gwt.client.EventConverter#convert(tigase.jaxmpp.core
	 * .client.observer.BaseEvent)
	 */
	@Override
	public UnknownEvent convert(Context<BaseEvent> e) {
		UnknownEvent rt = new UnknownEvent(this.typeName, e.getXmpp());
		return rt;
	}

}
