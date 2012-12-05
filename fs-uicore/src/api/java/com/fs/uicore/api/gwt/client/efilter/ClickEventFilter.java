package com.fs.uicore.api.gwt.client.efilter;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;

public class ClickEventFilter extends SimpleEventFilter implements
		Event.FilterI {

	public ClickEventFilter(UiObjectI src) {
		super(ClickEvent.TYPE, src);
	}

	@Override
	public <T extends Event> T filter(Event e) {
		if (null == super.filter(e)) {
			return null;
		}
		ClickEvent rt = (ClickEvent) e;

		return (T) rt;

	}

}
