/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class User extends NodeWrapper {

	/**
	 * @param ntype
	 * @param pts
	 */
	public User() {
		super(NodeTypes.USER);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.USER, User.class);
	}

}
