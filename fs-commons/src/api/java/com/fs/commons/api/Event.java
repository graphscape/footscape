/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 17, 2012
 */
package com.fs.commons.api;

import com.fs.commons.api.event.EventBusI;

/**
 * @author wu
 * 
 */
public class Event {

	private Object source;

	public Event(Object source) {

		this.source = source;
	}

	public <T> T getSource() {
		return (T) this.source;
	}

	/**
	 * Dec 17, 2012
	 */
	public void dispatch(EventBusI eventBus) {
		eventBus.put(this);
	}

	/**
	 * Dec 17, 2012
	 */
	public void dispatch(ContainerI container) {

		this.dispatch(container.getEventBus());
	}
}
