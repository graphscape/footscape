/**
 *  Dec 21, 2012
 */
package com.fs.engine.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.engine.api.DispatcherI;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;

/**
 * @author wuzhen
 * 
 */
public class EngineFactoryImpl extends ConfigurableSupport implements
		EngineFactoryI {

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		int es = this.config.getPropertyAsInt("engines", 6);

		for (int i = 0; i < es; i++) {
			ac.active("dispatcher-" + i);
			ac.active("engine-" + i);
		}
	}

	@Override
	public ServiceEngineI getEngine(int idx) {

		return this.container.find(ServiceEngineI.class, "engine-" + idx, true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.engine.api.EngineFactoryI#getDispatcher(int)
	 */
	@Override
	public DispatcherI<RequestI, ResponseI> getDispatcher(int idx) {
		return this.getEngine(idx).getDispatcher();
	}

}
