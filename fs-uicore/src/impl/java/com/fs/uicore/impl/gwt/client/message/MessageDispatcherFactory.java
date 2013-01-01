/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicore.impl.gwt.client.message;

import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wu
 * 
 */
public class MessageDispatcherFactory extends UiObjectSupport implements MessageDispatcherI.FactoryI {

	public MessageDispatcherFactory() {
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public MessageDispatcherI get(String name) {
		//
		String rname = "dispatcher-" + name;
		MessageDispatcherI rt = this.find(MessageDispatcherI.class, rname, false);
		if (rt == null) {
			rt = new MessageDispatcherImpl(rname);
			rt.parent(this);
		}
		return rt;
	}
}
