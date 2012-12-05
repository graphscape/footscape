/**
 * Jun 19, 2012
 */
package com.fs.commons.api;

import java.util.List;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.describe.DescribedI;

/**
 * @author wu
 * 
 */
public interface FinderI<T> extends DescribedI<FinderI<T>> {

	public T find(boolean force);

	public FinderI<T> withParent(boolean wp);

	public List<T> find();

	public void forEach(CallbackI<T, Boolean> cb);
}
