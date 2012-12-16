/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.expector.api;

/**
 * @author wu
 * 
 */
public interface GridedObjectManagerI<T extends GridedObjectI> {

	public String getName();

	public T addGridedObject(T go);

	public T removeGridedObject(String id);

	public T getGridedObject(String id);

}
