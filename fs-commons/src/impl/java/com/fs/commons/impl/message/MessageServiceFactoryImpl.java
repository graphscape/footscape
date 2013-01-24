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
 * @deprecated internal container should not support concurrent modify.
 */
public class MessageServiceFactoryImpl extends HasContainerSupport implements MessageServiceI.FactoryI {

	protected ReentrantLock lock = new ReentrantLock();

	public MessageServiceFactoryImpl() {
	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
	}

	@Override
	public MessageServiceI create(String name) {

		this.lock.lock();//
		try {
			// note,if find out of lock,a ConcurrentModifyException will raise.
			MessageServiceI rt = this.internal.find(MessageServiceI.class, name);

			if (rt != null) {
				return rt;
			}

			rt = new MessageServiceImpl(name);
			this.activeContext.newActiveContext(this.internal).active(name, rt);
			return rt;

		} finally {
			this.lock.unlock();
		}

	}

}
