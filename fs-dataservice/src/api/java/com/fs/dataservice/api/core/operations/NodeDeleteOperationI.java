/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core.operations;

import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.result.VoidResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeDeleteOperationI<W extends NodeWrapper> extends
		OperationI<NodeDeleteOperationI<W>, VoidResultI> {
	public NodeDeleteOperationI<W> nodeType(Class<W> cls);

	public NodeDeleteOperationI<W> uniqueId(String id);

}
