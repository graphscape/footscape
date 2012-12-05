/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core.operations;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.result.NodeResultI;

/**
 * @author wu
 * 
 */
public interface NodeGetOperationI extends
		OperationI<NodeGetOperationI, NodeResultI> {

	public NodeGetOperationI nodeType(NodeType ntype);

	public NodeGetOperationI uniqueId(String uid);

	public NodeGetOperationI execute(NodeType type, String uid);

}
