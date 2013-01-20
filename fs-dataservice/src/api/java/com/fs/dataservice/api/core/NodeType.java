/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.dataservice.api.core;

import com.fs.commons.api.Enum;

/**
 * @author wu
 * 
 */
public class NodeType extends Enum {
	
	/**
	 * @param v
	 */
	protected NodeType(String v) {
		super(v);
	}

	public static NodeType valueOf(String v) {
		return new NodeType(v);
	}

}
