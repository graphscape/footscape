/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 17, 2012
 */
package com.fs.gridservice.core.api.event;

import com.fs.commons.api.Event;
import com.fs.gridservice.core.api.DataGridI;

/**
 * @author wu
 * 
 */
public class BeforeDgCloseEvent extends Event {

	/**
	 * @param source
	 */
	public BeforeDgCloseEvent(DataGridI source) {
		super(source);
	}

}
