/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.core.impl;

/**
 * @author wu
 * 
 */
public interface DgCodecI<T, S> {

	public T readData(S s);

	public S writeData(T t);
	
}
