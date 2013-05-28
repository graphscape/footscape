/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.grounder.dataservice.api.wrapper;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public abstract class Definition extends NodeWrapper {

	public static final String DEF_VERSION = "version";

	/**
	 * @param pts
	 */
	public Definition(NodeType type) {
		super(type);
	}

}
