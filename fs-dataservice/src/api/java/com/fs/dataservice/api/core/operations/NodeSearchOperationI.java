/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core.operations;

import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu TODO separate a Query interface for different query style.
 */
// public interface NodeQueryOperationI<W extends NodeWrapper> extends
// OperationI<NodeQueryOperationI<W>, NodeQueryResultI<W>> {
public interface NodeSearchOperationI<W extends NodeWrapper> extends
		NodeQueryOperationI<NodeSearchOperationI<W>, W, NodeSearchResultI<W>> {

	public NodeSearchOperationI<W> first(int from);

	public NodeSearchOperationI<W> maxSize(int maxs);

	public NodeSearchOperationI<W> sort(String key);

	public NodeSearchOperationI<W> sort(String key, boolean desc);

	public NodeSearchOperationI<W> sortTimestamp(boolean desc);

	public NodeSearchOperationI<W> singleNewest(boolean nf);

	public int getFrom();

	public int getMaxSize();

}
