/**
 * Jun 14, 2012
 */
package com.fs.commons.api.config;

/**
 * @author wuzhen
 * 
 */
public interface ConfigurationProviderI {

	public void add(Configuration cfg);

	public Configuration getConfiguration(String id);
	
	public Configuration getConfiguration(String id, boolean cache);

}
