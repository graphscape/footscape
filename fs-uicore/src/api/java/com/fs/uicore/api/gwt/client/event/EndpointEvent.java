/**
 *  Dec 21, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public class EndpointEvent extends Event {

	public static final Type<EndpointEvent> TYPE = new Type<EndpointEvent>("endpoint");

	/**
	 * @param type
	 */
	public EndpointEvent(Type<? extends EndpointEvent> type, EndPointI source) {
		super(type, source);

	}

	protected EndpointEvent(Type<? extends EndpointEvent> type, EndPointI source, MessageData md) {
		super(type, source, md);
	}

	public EndPointI getEndPoint() {
		return (EndPointI) this.source;
	}

	public EndPointI getChannel() {
		return (EndPointI) this.source;
	}

}
