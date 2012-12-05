/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core.operations;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.result.VoidResultI;

/**
 * @author wu
 * 
 */
public interface NodeDeleteOperationI extends
		OperationI<NodeDeleteOperationI, VoidResultI> {

	public NodeDeleteOperationI nodeType(NodeType ntype);

	public NodeDeleteOperationI uniqueId(String id);

	public NodeDeleteOperationI execute(NodeType type, String uid);

}
