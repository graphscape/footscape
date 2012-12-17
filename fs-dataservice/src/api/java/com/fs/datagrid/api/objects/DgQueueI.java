/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.api.objects;

import java.util.concurrent.TimeUnit;

import com.fs.datagrid.api.DgCollectionI;

/**
 * @author wuzhen
 * 
 */
public interface DgQueueI<T> extends DgCollectionI {

	public T take();// not use this if possible

	public T poll(long time, TimeUnit tu);

	public void offer(T t);

}
