/**
 *  Dec 21, 2012
 */
package com.fs.engine.api;

/**
 * @author wuzhen
 * 
 */
public interface EngineFactoryI {

	public ServiceEngineI getEngine(int idx);

	public DispatcherI<RequestI, ResponseI> getDispatcher(int idx);

}
