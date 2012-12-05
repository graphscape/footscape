/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.api.core.result;

import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

public interface NodeResultI extends ResultI<NodeResultI, NodeI> {

	public NodeI getNode(boolean force);

	public <T extends NodeWrapper> T wrapNode(Class<T> cls, boolean force);

}