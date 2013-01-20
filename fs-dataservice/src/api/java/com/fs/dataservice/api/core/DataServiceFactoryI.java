/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 20, 2013
 */
package com.fs.dataservice.api.core;

import com.fs.dataservice.api.core.meta.DataSchema;

/**
 * @author wu
 * 
 */
public interface DataServiceFactoryI {

	public DataServiceI getDataService();

	public DataSchema getSchema();

}
