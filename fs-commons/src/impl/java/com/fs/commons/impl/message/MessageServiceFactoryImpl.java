/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.message;

import java.util.concurrent.locks.ReentrantLock;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.support.HasContainerSupport;

/**
 * @author wuzhen
 * 
 */
public class MessageServiceFactoryImpl extends HasContainerSupport implements
		MessageServiceI.FactoryI {

	protected ReentrantLock lock = new ReentrantLock();

	public MessageServiceFactoryImpl() {
	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
	}

	@Override
	public MessageServiceI create(String name) {
		MessageServiceI rt = this.internal.find(MessageServiceI.class, name);

		if (rt != null) {
			return rt;
		}

		this.lock.lock();
		try {
			rt = this.internal.find(MessageServiceI.class, name);
			if (rt != null) {
				return rt;
			}
			rt = new MessageServiceImpl(name);
			this.activeContext.newActiveContext(this.internal).active(name, rt);

		} finally {
			this.lock.unlock();
		}

		return rt;
	}

}
