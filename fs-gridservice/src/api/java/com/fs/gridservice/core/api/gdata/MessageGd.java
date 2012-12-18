/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.core.api.gdata;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.ProxyMessageSupport;
import com.fs.gridservice.core.api.WrapperGdI;

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
	 * @see com.fs.gridservice.core.api.WrapperGdI#setTarget(java.lang.Object)
	 */
	@Override
	public void setTarget(MessageI t) {
		this.target = t;
	}

}
