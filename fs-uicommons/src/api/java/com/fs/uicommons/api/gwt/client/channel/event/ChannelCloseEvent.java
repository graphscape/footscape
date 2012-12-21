/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.api.gwt.client.channel.event;

import com.fs.uicommons.api.gwt.client.channel.ChannelI;

/**
 * @author wuzhen
 * 
 */
public class ChannelCloseEvent extends ChannelEvent {

	public static final Type<ChannelCloseEvent> TYPE = new Type<ChannelCloseEvent>(
			ChannelEvent.TYPE);

	/**
	 * @param type
	 */
	public ChannelCloseEvent(ChannelI c) {
		super(TYPE, c);
	}

}
