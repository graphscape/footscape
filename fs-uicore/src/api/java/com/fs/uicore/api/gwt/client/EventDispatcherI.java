/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.FilterI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.Event.Type;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public interface EventDispatcherI {

	public <E extends Event> void addHandler(UiObjectI src, HandlerI<E> l);

	public <E extends Event> void addHandler(UiObjectI src, Type<E> ec,
			HandlerI<E> l);

	public <E extends Event> void addHandler(FilterI ef, HandlerI<E> eh);

	public void dispatch(Event e);
}
