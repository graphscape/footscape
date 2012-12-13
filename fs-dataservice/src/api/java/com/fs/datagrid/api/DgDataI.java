/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.datagrid.api;

import org.json.simple.JSONValue;

/**
 * @author wu
 * 
 */
public interface DgDataI {

	public JSONValue readData();

	public void writeData(JSONValue json);

}
