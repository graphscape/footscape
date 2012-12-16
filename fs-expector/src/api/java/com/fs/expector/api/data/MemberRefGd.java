/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.expector.api.data;

import com.fs.expector.api.GridMemberI;

/**
 * @author wu
 * 
 */
public class MemberRefGd extends ObjectRefGd<GridMemberI> {

	public static final String PK_LOCAL_EVENTQUEUE = "localEventQueueName";

	/**
	 * @param id
	 * @param mid
	 */
	public MemberRefGd(String mid) {
		super(mid, mid);
		this.setProperty(PK_LOCAL_EVENTQUEUE, "local-event-queue." + mid);
	}

	public String getLocalEventQueueName() {
		return (String) this.getProperty(PK_LOCAL_EVENTQUEUE, true);
	}
}
