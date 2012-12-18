/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.gridservice.commons.api.EventDispatcherI;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.impl.support.EventDispatcherSupport;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wu
 * 
 */
public class LocalEventDispatcher extends EventDispatcherSupport implements EventDispatcherI {

	protected static final Logger LOG = LoggerFactory.getLogger(LocalEventDispatcher.class);

	/*
	 * Dec 17, 2012
	 */
	@Override
	protected DgQueueI<EventGd> resolveEventQueue() {
		//
		return this.facade.getLocalMemberEventQueue();
	}

}
