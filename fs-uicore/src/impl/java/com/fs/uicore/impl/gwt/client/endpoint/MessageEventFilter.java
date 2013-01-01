/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.impl.gwt.client.endpoint;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

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
		if (!(e instanceof EndpointMessageEvent)) {
			return null;
		}
		EndpointMessageEvent me = (EndpointMessageEvent) e;
		String path = me.getPath();
		if (this.path != null) {
			if (!path.startsWith(this.path)) {// TODO arry path[]
				return null;
			}
		}
		return (T) e;

	}

}
