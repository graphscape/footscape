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
public class Participant extends NodeWrapper {

	public static final String ROLE_ID = "roleId";

	/**
	 * @param pts
	 */
	public Participant() {
		super(NodeTypes.ACTIVITY);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.PARTICIPANT, Participant.class).field(ROLE_ID);

	}

}
