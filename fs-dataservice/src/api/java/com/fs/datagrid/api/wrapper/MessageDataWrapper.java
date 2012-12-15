/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.api.wrapper;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.ProxyMessageSupport;
import com.fs.datagrid.api.DataWrapperI;

/**
 * @author wuzhen
 * 
 */
public class MessageDataWrapper extends ProxyMessageSupport implements DataWrapperI<MessageI> {

	public MessageDataWrapper() {
		this(MessageSupport.newMessage());
	}

	/**
	 * @param t
	 */
	public MessageDataWrapper(MessageI msg) {
		super(msg);
	}

	@Override
	public MessageI getTarget() {
		return this.target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.DataWrapperI#setTarget(java.lang.Object)
	 */
	@Override
	public void setTarget(MessageI t) {
		this.target = t;
	}

}
