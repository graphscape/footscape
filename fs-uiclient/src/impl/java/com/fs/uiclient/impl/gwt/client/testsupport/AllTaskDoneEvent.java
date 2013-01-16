/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 12, 2013
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class AllTaskDoneEvent extends Event {

	public static final Type<AllTaskDoneEvent> TYPE = new Type<AllTaskDoneEvent>("all-task-done");

	/**
	 * @param path
	 * @param src
	 * @param msg
	 */
	protected AllTaskDoneEvent() {
		super(TYPE);
	}

}
