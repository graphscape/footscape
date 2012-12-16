/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.expector.api.data;

import com.fs.datagrid.api.wrapper.PropertiesDataWrapper;
import com.fs.expector.api.GridedDataI;

/**
 * @author wu
 * 
 */
public class EntityGd extends PropertiesDataWrapper implements GridedDataI {

	public String getId() {
		return (String) this.getProperty("_id", true);
	}

}
