/**
 * Jun 11, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.service.HandlerI;

/**
 * @author wuzhen
 * 
 */
public interface RequestHandlerI extends ConfigurableI,
		HandlerI<HandleContextI> {
	public void handle(HandleContextI sc);
}
