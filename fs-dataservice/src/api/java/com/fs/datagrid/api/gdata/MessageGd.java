/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.api.gdata;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.ProxyMessageSupport;
import com.fs.datagrid.api.WrapperGdI;

/**
 * @author wuzhen
 * 
 */
public class MessageGd extends ProxyMessageSupport implements WrapperGdI<MessageI> {

	public MessageGd() {
		this(MessageSupport.newMessage());
	}

	/**
	 * @param t
	 */
	public MessageGd(MessageI msg) {
		super(msg);
	}

	@Override
	public MessageI getTarget() {
		return this.target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.WrapperGdI#setTarget(java.lang.Object)
	 */
	@Override
	public void setTarget(MessageI t) {
		this.target = t;
	}

}
