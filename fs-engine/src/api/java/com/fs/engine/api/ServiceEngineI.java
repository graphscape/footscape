/**
 * Jun 11, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.service.ServiceI;

/**
 * @author wuzhen
 * 
 */
public interface ServiceEngineI extends ServiceI<RequestI, ResponseI>,
		PopulatorI.FactoryI {

	public void start();

	public void shutdown();

}
