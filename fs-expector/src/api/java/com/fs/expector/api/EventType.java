/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.api;

import com.fs.commons.api.lang.ObjectUtil;

/**
 * @author wu
 * 
 */
public class EventType {

	private String value;

	private EventType(String type) {
		this.value = type;
	}

	public static EventType valueOf(String type) {
		return new EventType(type);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public int hashCode() {
		//
		return this.value.hashCode();
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof EventType)) {
			return false;
		}
		return ObjectUtil.nullSafeEquals(this.value, ((EventType) obj).value);
	}

	/**
	 * Dec 16, 2012
	 */
	public String name() {
		//
		return this.value;
	}
}
