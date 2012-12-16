/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.expector.api.data;

import java.util.UUID;

import com.fs.commons.api.value.PropertiesI;
import com.fs.datagrid.api.gdata.PropertiesGd;
import com.fs.expector.api.GridedDataI;

/**
 * @author wu
 * 
 */
public class EntityGd extends PropertiesGd implements GridedDataI {

	public EntityGd() {
		this.setProperty("_id", UUID.randomUUID().toString());
	}

	public EntityGd(PropertiesI<Object> pts) {
		this.setProperties(pts);
		String id = this.getId(false);
		if (id == null) {
			this.setProperty("_id", UUID.randomUUID().toString());
		}
	}

	public String getId() {
		return this.getId(true);
	}

	protected String getId(boolean force) {
		return (String) this.getProperty("_id", force);
	}

}
