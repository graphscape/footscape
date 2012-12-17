/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.expector.api.EventDispatcherI;
import com.fs.expector.api.data.EventGd;
import com.fs.expector.impl.support.EventDispatcherSupport;

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
