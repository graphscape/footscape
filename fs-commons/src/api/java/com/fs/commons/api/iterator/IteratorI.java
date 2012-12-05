/**
 * Jun 18, 2012
 */
package com.fs.commons.api.iterator;

import java.util.Iterator;
import java.util.List;

import com.fs.commons.api.callback.CallbackI;

/**
 * @author wu
 * 
 */
public interface IteratorI<T> extends Iterator<T> {

	public void forEach(CallbackI<T, Boolean> fe);

	public T single();

	public T single(boolean force);
	
	public List<T> getAsList();
}
