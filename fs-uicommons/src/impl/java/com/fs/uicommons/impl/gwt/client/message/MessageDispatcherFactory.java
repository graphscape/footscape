/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.message;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicommons.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wu
 * 
 */
public class MessageDispatcherFactory extends UiObjectSupport implements MessageDispatcherI.FactoryI {

	List<MessageDispatcherI> dispatchers;

	public MessageDispatcherFactory() {
		this.dispatchers = new ArrayList<MessageDispatcherI>();
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public MessageDispatcherI get(int idx) {
		//
		String name = "dispatcher-" + idx;
		MessageDispatcherI rt = this.find(MessageDispatcherI.class, name, false);
		if (rt == null) {
			rt = new MessageDispatcherImpl(name);
			rt.parent(this);
		}
		return rt;
	}
}
