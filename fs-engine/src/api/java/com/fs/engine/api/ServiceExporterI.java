/**
 * Jun 16, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.config.ConfigurableI;

/**
 * @author wu
 * 
 */
public interface ServiceExporterI extends ConfigurableI {
	
	public void start();

	public void shutdown();
}
