/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core.operations;

import java.util.Date;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu TODO separate a Query interface for different query style.
 */
public interface NodeQueryOperationI<W extends NodeWrapper> extends
		OperationI<NodeQueryOperationI<W>, NodeQueryResultI<W>> {

	public NodeType getNodeType(boolean force);

	public NodeQueryOperationI<W> nodeType(NodeType ntype);

	public NodeQueryOperationI<W> nodeType(Class<W> cls);

	public NodeQueryOperationI<W> propertyNotEq(String key, Object value);
	
	public NodeQueryOperationI<W> propertyEq(String key, Object value);

	public NodeQueryOperationI<W> propertyGt(String key, Object value, boolean include);

	public NodeQueryOperationI<W> propertyMatch(String key, String pharse);

	public NodeQueryOperationI<W> propertyMatch(String key, String pharse, int slop);

	public NodeQueryOperationI<W> propertyLt(String key, Object value, boolean include);

	public NodeQueryOperationI<W> propertyRange(String key, Object from, boolean includeFrom, Object to,
			boolean includeTo);

	public NodeQueryOperationI<W> uniqueId(String uid);

	public NodeQueryOperationI<W> id(String id);

	public NodeQueryOperationI<W> first(int from);

	public NodeQueryOperationI<W> maxSize(int maxs);

	public NodeQueryOperationI<W> sort(String key);

	public NodeQueryOperationI<W> sort(String key, boolean desc);

	public NodeQueryOperationI<W> sortTimestamp(boolean desc);

	public NodeQueryOperationI<W> singleNewest(boolean nf);

	public NodeQueryOperationI<W> timestampRange(Date from, boolean includeFrom, Date to, boolean includeTo);

	public String getUniqueId();

	public int getFrom();

	public int getMaxSize();

}
