/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core.operations;

import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.result.NodeCreateResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeCreateOperationI<W extends NodeWrapper> extends
		NodeOperationI<NodeCreateOperationI<W>, W, NodeCreateResultI> {

	public static final String PK_REFRESH_AFTER_CREATE = "refreshAfterCreate";

	public NodeCreateOperationI<W> refreshAfterCreate(boolean refreshAfterCreate);

	public NodeCreateOperationI<W> execute(NodeType type, PropertiesI<Object> pts);

	public NodeCreateOperationI<W> uniqueId(String id);

	public NodeCreateOperationI<W> properties(PropertiesI<Object> pts);

	public NodeCreateOperationI<W> property(String key, Object value);

	public PropertiesI<Object> getNodeProperties();

	public <T extends NodeWrapper> NodeCreateOperationI<W> wrapper(T nw);

}
