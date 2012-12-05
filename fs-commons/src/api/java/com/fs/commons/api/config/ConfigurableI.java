/**
 * Jun 14, 2012
 */
package com.fs.commons.api.config;

/**
 * @author wuzhen
 * 
 */
public interface ConfigurableI {

	public void configure(Configuration cfg);

	public void configure(String id, ConfigurationProviderI cp);

	public Configuration getConfiguration();

}
