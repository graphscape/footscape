/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.expector.api.data;

import com.fs.datagrid.api.gdata.PropertiesGd;
import com.fs.expector.api.GridedDataI;
import com.fs.expector.api.GridedObjectI;

/**
 * @author wu
 * 
 */
public class ObjectRefGd<T extends GridedObjectI> extends PropertiesGd implements GridedDataI {
	
	public static final String ID = "_id";
	
	public static final String MID = "_memberId";

	public ObjectRefGd(String id, String mid) {
		this.setProperty(ID, id);
		this.setProperty(MID, mid);
	}

	public String getId() {
		return (String) this.getProperty(ID);
	}

	public String getMemberId() {
		return (String) this.getProperty(MID);

	}

}
