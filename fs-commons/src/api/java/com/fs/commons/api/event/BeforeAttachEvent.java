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
public class BeforeAttachEvent extends Event {

	/**
	 * @param type
	 * @param source
	 */
	public BeforeAttachEvent(Object source) {
		super(source);
	}

}
