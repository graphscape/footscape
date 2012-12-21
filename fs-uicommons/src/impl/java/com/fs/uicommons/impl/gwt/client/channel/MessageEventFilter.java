/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.impl.gwt.client.channel;

import com.fs.uicommons.api.gwt.client.channel.event.ChannelMessageEvent;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class MessageEventFilter implements Event.FilterI {

	protected String path;

	public MessageEventFilter(String p) {
		this.path = p;
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public <T extends Event> T filter(Event e) {
		if (!(e instanceof ChannelMessageEvent)) {
			return null;
		}
		ChannelMessageEvent me = (ChannelMessageEvent) e;
		String path = me.getPath();
		if (this.path != null) {
			if (!path.startsWith(this.path)) {// TODO arry path[]
				return null;
			}
		}
		return (T) e;

	}

}
