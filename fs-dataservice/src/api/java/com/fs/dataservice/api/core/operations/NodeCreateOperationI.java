/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core.operations;

import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeOperationI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.result.NodeCreateResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeCreateOperationI extends NodeOperationI<NodeCreateOperationI,NodeCreateResultI> {

	public static final String PK_REFRESH_AFTER_CREATE = "refreshAfterCreate";

	public NodeCreateOperationI refreshAfterCreate(boolean refreshAfterCreate);

	public NodeCreateOperationI execute(NodeType type, PropertiesI<Object> pts);

	public PropertiesI<Object> getNodeProperties();

	public <T extends NodeWrapper> NodeCreateOperationI wrapper(T nw);

}
