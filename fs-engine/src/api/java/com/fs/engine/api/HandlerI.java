/**
 * Jun 11, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.config.ConfigurableI;

/**
 * @author wuzhen
 * 
 */
public interface HandlerI extends ConfigurableI {
	public void handle(HandleContextI sc);
}
