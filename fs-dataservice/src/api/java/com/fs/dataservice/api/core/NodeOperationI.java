/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core;

import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeOperationI<O extends NodeOperationI<O, T>, T extends ResultI<T, ?>>
		extends OperationI<O, T> {

	public O nodeType(NodeType ntype);

	public O nodeType(Class<? extends NodeWrapper> cls);

	public O uniqueId(String id);

	public O properties(PropertiesI<Object> pts);

	public O property(String key, Object value);

	public PropertiesI<Object> getNodeProperties();

}
