/**
 *  Dec 21, 2012
 */
package com.fs.engine.impl;

import java.util.concurrent.locks.ReentrantLock;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.engine.api.DispatcherI;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.impl.dispatcher.DispatcherImpl;

/**
 * @author wuzhen
 * 
 */
public class EngineFactoryImpl extends ConfigurableSupport implements
		EngineFactoryI {

	protected ReentrantLock lock = new ReentrantLock();

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);

	}

	@Override
	public ServiceEngineI getEngine(String name) {
		String ename = "engine-" + name;
		ServiceEngineI rt = this.container.find(ServiceEngineI.class, ename,
				false);
		if (rt != null) {
			return rt;
		}
		this.lock.lock();
		try {
			rt = this.container.find(ServiceEngineI.class, ename, false);
			if (rt != null) {
				return rt;
			}
			ActiveContext ac = this.newActiveContext();
			String disName = "dispatcher-" + name;
			{

				Configuration cfg = Configuration.properties(disName);// NOTE?
				ac.activitor().object(new DispatcherImpl()).name(cfg.getName())
						.configuration(cfg).active();
			}
			{

				Configuration cfg = Configuration.properties(ename);// NOTE?
				cfg.setProperty("dispatcher", disName);
				ac.activitor().object(new EngineImpl()).name(cfg.getName())
						.configuration(cfg).active();
			}

		} finally {
			this.lock.unlock();
		}
		rt = this.container.find(ServiceEngineI.class, ename, true);
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.engine.api.EngineFactoryI#getDispatcher(int)
	 */
	@Override
	public DispatcherI<RequestI, ResponseI> getDispatcher(String name) {

		return this.getEngine(name).getDispatcher();

	}

}
