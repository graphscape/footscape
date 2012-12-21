/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.channel.event;

import com.fs.uicommons.api.gwt.client.channel.ChannelI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class ChannelMessageEvent extends ChannelEvent {

	public static final Type<ChannelMessageEvent> TYPE = new Type<ChannelMessageEvent>();

	protected MessageData message;

	/**
	 * @param tem
	 * @param type
	 */
	public ChannelMessageEvent(ChannelI tem, MessageData msg) {
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
