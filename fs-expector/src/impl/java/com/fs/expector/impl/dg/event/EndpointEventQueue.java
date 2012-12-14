/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.event;

import com.fs.expector.api.event.EndpointEventI;
import com.fs.expector.api.event.EventQueueI;
import com.fs.expector.impl.dg.support.DgQueueImplSupport;

/**
 * @author wuzhen
 * 
 */
public class EndpointEventQueue extends
		DgQueueImplSupport<EndpointEventI, EndpointEventImpl> implements
		EventQueueI<EndpointEventI> {

	/**
	 * @param name
	 * @param icls
	 * @param wcls
	 */
	public EndpointEventQueue() {
		super(EndpointEventI.class, EndpointEventImpl.class);

	}

	@Override
	public void offer(EndpointEventI e) {
		this.target.offer(e);
	}

	@Override
	public EndpointEventI take() {
		return this.target.take();
	}

}
