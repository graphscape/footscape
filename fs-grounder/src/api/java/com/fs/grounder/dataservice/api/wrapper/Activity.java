/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.grounder.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.grounder.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class Activity extends NodeWrapper {

	public static final String DEFID = "defId";
	
	public static final String DEFVER = "defVer";

	/**
	 * @param pts
	 */
	public Activity() {
		super(NodeTypes.ACTIVITY);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.ACTIVITY, Activity.class).field(DEFID).field(DEFVER);
		

	}

}
