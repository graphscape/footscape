/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.api.data;

import java.util.UUID;

import com.fs.commons.api.HasIdI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.GridedDataI;
import com.fs.gridservice.core.api.gdata.PropertiesGd;

/**
 * @author wu
 *         <p>
 *         Note sub class must provide at least these two constructors.
 */
public class EntityGd extends PropertiesGd implements GridedDataI, HasIdI {

	public static final String PK_ID = "_id";

	public EntityGd() {
		this(null, null);
	}

	public EntityGd(String id) {
		this(id, null);
	}

	public EntityGd(PropertiesI<Object> pts) {
		this(null, pts);
	}

	protected EntityGd(String id, PropertiesI<Object> pts) {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
		this.setProperty(PK_ID, id);//
		if (pts != null) {
			this.setProperties(pts);
		}
	}

	public boolean isIdEquals(EntityGd e) {
		return this.getId().endsWith(e.getId());

	}

	@Override
	public String getId() {
		return this.getId(true);
	}

	protected String getId(boolean force) {
		return (String) this.getProperty(PK_ID, force);
	}

}
