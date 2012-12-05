/**
 * Jun 16, 2012
 */
package com.fs.commons.api.server;

import com.fs.commons.api.config.ConfigurableI;

/**
 * @author wu
 * 
 */
public interface ServerI extends ConfigurableI {

	public void start();

	public void cmd(String cmd);

	public void shutdown();

}
