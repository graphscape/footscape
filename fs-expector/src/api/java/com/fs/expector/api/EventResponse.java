/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.api;


/**
 * @author wu
 * 
 */
public class EventResponse<E extends EventWrapper, T extends EventWrapper> {

	protected E source;
	
	protected T event;

	public EventResponse(E e,T t){
		this.source = e;
		this.event = t;
	}
	
	public T getResponse() {
		return event;
	}
	
}
