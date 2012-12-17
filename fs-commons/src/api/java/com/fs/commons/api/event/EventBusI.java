/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 17, 2012
 */
package com.fs.commons.api.event;

import com.fs.commons.api.Event;

/**
 * @author wu
 * 
 */
public interface EventBusI {

	public <T extends Event> void addListener(Class<T> cls, ListenerI<T> l);

	public void put(Event e);
}
