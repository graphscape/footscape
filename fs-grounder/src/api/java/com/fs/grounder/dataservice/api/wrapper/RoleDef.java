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
public class RoleDef extends Definition {

	public static final String ACTIVITY_DEF_ID = "activityDefId";
	
	public static final String MAX = "maxParticipant";

	/**
	 * @param pts
	 */
	public RoleDef() {
		super(NodeTypes.ACTIVITY);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.ACTIVITY, RoleDef.class).field(DEF_VERSION).field(ACTIVITY_DEF_ID);

		
	}

}
