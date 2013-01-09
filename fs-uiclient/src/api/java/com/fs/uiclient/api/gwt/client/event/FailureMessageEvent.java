/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.fs.uiclient.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wu
 * 
 */
public class FailureMessageEvent extends MessageEvent {

	public static final Type<FailureMessageEvent> TYPE = new Type<FailureMessageEvent>(MessageEvent.TYPE, "failure");

	/**
	 * @param type
	 */
	public FailureMessageEvent(EndPointI src, MessageData md) {
		super(TYPE, src, md);
	}

}
