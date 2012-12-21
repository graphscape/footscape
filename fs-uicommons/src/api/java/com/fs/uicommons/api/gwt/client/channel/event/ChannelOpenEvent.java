/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.api.gwt.client.channel.event;

import com.fs.uicommons.api.gwt.client.channel.ChannelI;

/**
 * @author wuzhen
 * 
 */
public class ChannelOpenEvent extends ChannelEvent {

	public static final Type<ChannelOpenEvent> TYPE = new Type<ChannelOpenEvent>(
			ChannelEvent.TYPE);

	/**
	 * @param type
	 */
	public ChannelOpenEvent(ChannelI c) {
		super(TYPE, c);
	}

}
