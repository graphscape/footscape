/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.service;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.support.HasContainerSupport;

/**
 * @author wuzhen
 * 
 */
public class DispatcherFactoryImpl extends HasContainerSupport implements
		DispatcherI.FactoryI {

	@Override
	public <T> DispatcherI<T> create(String name) {

		DispatcherI<T> rt = this.internal.find(DispatcherI.class, name);
		if (rt != null) {
			throw new FsException("dispatcher exists:" + name);
		}

		rt = new DispatcherImpl<T>(name);
		this.activeContext.newActiveContext(internal).active(name, rt);
		return rt;

	}

}
