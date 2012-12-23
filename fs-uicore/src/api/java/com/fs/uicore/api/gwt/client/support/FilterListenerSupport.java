/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 23, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public class FilterListenerSupport<E extends Event> implements EventHandlerI<E> {

	protected Event.Type<?> type;
	protected Class sourceClass;

	public FilterListenerSupport(Event.Type<?> type, Class srcType) {
		this.type = type;
		this.sourceClass = srcType;
	}

	@Override
	public void handle(E e) {
		if (!e.isMatch(type, this.sourceClass)) {
			return;
		}
		this.onEventInternal(e);
	}

	protected void onEventInternal(E e) {

	}

}
