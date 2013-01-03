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
public class SuccessMessageEvent extends MessageEvent {

	public static final Type<SuccessMessageEvent> TYPE = new Type<SuccessMessageEvent>(MessageEvent.TYPE);

	/**
	 * @param type
	 */
	public SuccessMessageEvent(EndPointI src, MessageData md) {
		super(TYPE, src, md);
	}

}
