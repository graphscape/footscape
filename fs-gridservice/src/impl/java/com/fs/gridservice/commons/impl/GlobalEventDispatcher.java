/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.gridservice.commons.api.GlobalEventDispatcherI;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.support.EventDispatcherSupport;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wu
 * 
 */
public class GlobalEventDispatcher extends EventDispatcherSupport implements GlobalEventDispatcherI {

	protected static final Logger LOG = LoggerFactory.getLogger(GlobalEventDispatcher.class);

	/*
	 * Dec 17, 2012
	 */
	@Override
	protected DgQueueI<EventGd> resolveEventQueue() {
		//
		return this.facade.getGlogalEventQueue();
	}

}
