/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.grounder.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.grounder.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class ActivityDef extends Definition {

	public static final String NAME = "name";
	
	public static final String SUPER_ID = "superId";
	
	public static final String IS_FINAL = "isFinal";
	

	/**
	 * @param pts
	 */
	public ActivityDef() {
		super(NodeTypes.ACTIVITY);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.ACTIVITY, ActivityDef.class).field(Definition.DEF_VERSION).field(NAME).field(SUPER_ID).field(IS_FINAL);
	
	}

}
