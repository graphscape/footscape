/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api;

import com.fs.gridservice.commons.api.data.EventGd;

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

	public void setPayload(String key, Object value) {
		this.target.setPayload(key, value);
	}

}
