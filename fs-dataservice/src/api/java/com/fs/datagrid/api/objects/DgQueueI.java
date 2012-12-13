/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.api.objects;

import com.fs.datagrid.api.DgCollectionI;

/**
 * @author wuzhen
 * 
 */
public interface DgQueueI<T> extends DgCollectionI {

	public T take();

	public void offer(T t);
	
}
