/**
 * Sep 21, 2012
 */
package com.fs.uicore.api.gwt.client.config;

import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * 
 * @author wuzhen
 *         <p>
 *         This is a isolated function.read the configuration from server side.
 *         <p>
 *         Note,there is no way to get all the children configurations by one
 *         id.
 * @deprecated configure should be load at client init. server side configure
 *             should not be access randomly.
 */

public interface ConfigurationFactoryI extends UiObjectI {

	public UiConfiguration getDefaultConfiguration();

	// the current configuration data that is loaded before.
	public UiConfiguration getConfiguration(String id, boolean force);

	// get the configuration from server side and cache it if client have not
	// cached.
	public void getConfiguration(String id,
			UiCallbackI<UiConfiguration, Object> cb);

	// make sure the required ids configuration is cached and notify it's ready.
	public void getConfigurationList(String[] ids,
			UiCallbackI<ConfigurationFactoryI, Object> cb);

}
