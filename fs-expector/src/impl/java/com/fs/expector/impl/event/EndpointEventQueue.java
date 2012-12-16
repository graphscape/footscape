/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.event;

import com.fs.expector.api.data.EventGd;
import com.fs.expector.api.events.MemberEventQueueI;
import com.fs.expector.impl.support.QueueSupport;

/**
 * @author wuzhen
 * 
 */
public class EndpointEventQueue extends QueueSupport<EventGd> implements MemberEventQueueI<EventGd> {

	/**
	 * @param name
	 * @param icls
	 * @param wcls
	 */
	public EndpointEventQueue() {
		super(EventGd.class);

	}

	@Override
	public void offer(EventGd e) {
		this.target.offer(e);
	}

	@Override
	public EventGd take() {
		return this.target.take();
	}

}
