/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.expapp.wrapper;

import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.api.expapp.NodeTypes;

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
	public static void config(NodeConfigurations cfs) {
		cfs.addConfig(NodeTypes.USER, User.class);
	}

}
