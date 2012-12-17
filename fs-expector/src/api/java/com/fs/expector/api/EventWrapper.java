/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.api;

import com.fs.expector.api.data.EventGd;

/**
 * @author wu
 * 
 */
public abstract class EventWrapper {

	protected EventGd target;

	public EventWrapper(EventGd target) {
		this.target = target;
	}

	public EventGd getTarget() {
		return target;
	}

}