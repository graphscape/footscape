/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core.result;

import java.util.List;

import com.fs.dataservice.api.core.ResultI;

/**
 * @author wu
 * 
 */
public interface ListResultI<R extends ResultI<R, List<T>>, T> extends
		ResultI<R, List<T>> {
	public List<T> list();

	public T single(boolean force);

	public T first(boolean force);

	public int size();

}
