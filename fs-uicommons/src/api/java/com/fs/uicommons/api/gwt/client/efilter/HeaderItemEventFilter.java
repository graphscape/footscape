/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.api.gwt.client.efilter;

import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.efilter.SimpleEventFilter;

/**
 * @author wu
 * 
 */
public class HeaderItemEventFilter extends SimpleEventFilter {

	protected Path path;

	public HeaderItemEventFilter(Path path) {
		super(HeaderItemEvent.TYPE);
		this.path = path;
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public <T extends Event> T filter(Event e) {
		//
		T rt = super.filter(e);
		if (rt == null) {
			return rt;
		}
		HeaderItemEvent he = (HeaderItemEvent) e;
		if (this.path == null || he.getModel().getPath().equals(this.path)) {
			return rt;
		}
		return null;

	}

}
