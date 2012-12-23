/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.endpoint.event;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class EndpointMessageEvent extends EndpointEvent {

	public static final Type<EndpointMessageEvent> TYPE = new Type<EndpointMessageEvent>(EndpointEvent.TYPE);

	protected MessageData message;

	/**
	 * @param tem
	 * @param type
	 */
	public EndpointMessageEvent(EndPointI tem, MessageData msg) {
		super(TYPE, tem);
		this.message = msg;
	}

	/**
	 * @return the message
	 */
	public MessageData getMessage() {
		return message;
	}

	public String getPath() {
		return this.message.getHeader("path", true);
	}

}
