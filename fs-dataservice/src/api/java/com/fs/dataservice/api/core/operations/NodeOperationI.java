/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core.operations;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeOperationI<O extends NodeOperationI<O, W, R>, W extends NodeWrapper, R extends ResultI<R, ?>>
		extends OperationI<O, R> {

	public NodeType getNodeType(boolean force);

	public O nodeType(NodeType ntype);

	public O nodeType(Class<W> cls);

}
