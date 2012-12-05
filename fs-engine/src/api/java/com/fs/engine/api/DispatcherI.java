/**
 * Jun 14, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.filter.FilterI;

/**
 * @author wuzhen
 * 
 */
public interface DispatcherI<REQ, RES> extends ConfigurableI,
		PopulatorI.FactoryI {

	public void dispatch(FilterI.Context<REQ, RES> fc);

	public ContainerI getHandlerContainer();

}
