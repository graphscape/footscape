/**
 *  Dec 21, 2012
 */
package com.fs.uicommons.api.gwt.client.channel.event;

import com.fs.uicommons.api.gwt.client.channel.ChannelI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class ChannelEvent extends Event {

	public static final Type<ChannelEvent> TYPE = new Type<ChannelEvent>();

	/**
	 * @param type
	 */
	public ChannelEvent(Type<? extends ChannelEvent> type, ChannelI source) {
		super(type, source);

	}

	public ChannelI getChannel() {
		return (ChannelI) this.source;
	}

}
