/**
 * Jun 22, 2012
 */
package com.fs.engine.impl.context;

import com.fs.commons.api.context.support.ContextSupport;
import com.fs.engine.api.EngineContextI;
import com.fs.engine.api.ServiceEngineI;

/**
 * @author wu
 * 
 */
public class EngineContext extends ContextSupport implements EngineContextI {
	private static final String K_SE = "_SERVICE_ENGINE";

	public EngineContext(ServiceEngineI se) {
		this.setProperty(K_SE, se);
	}

	/* */

	@Override
	public ServiceEngineI getEngine() {

		return this.getProperty(ServiceEngineI.class, K_SE);

	}

}
