/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 29, 2012
 */
package com.fs.dataservice.api.core.wrapper;

/**
 * @author wu
 *
 */
public interface PropertyConverterI<S,T> {
	
	public T convertFromStore(S s);
	
}
