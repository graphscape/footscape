/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core.support;

import com.fs.commons.api.support.MapProperties;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;

/**
 * @author wu
 * 
 */
public class NodeSupport extends MapProperties<Object> implements NodeI {

	public NodeSupport(NodeType type, String uid) {
		this.setProperty(PK_TYPE, type);
		this.setProperty(PK_UNIQUE_ID, uid);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public String getUniqueId() {
		//
		return (String) this.getProperty(PK_UNIQUE_ID);
	}

	@Override
	public String getId() {
		//
		return (String) this.getProperty(PK_ID);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeType getType() {
		//
		return (NodeType) this.getProperty(PK_TYPE);

	}

	/*
	 *Nov 18, 2012
	 */
	@Override
	public String getTimestamp() {
		// 
		return (String)this.getProperty(PK_TIMESTAMP);
		
	}

}
