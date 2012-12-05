/**
 * Jun 22, 2012
 */
package com.fs.commons.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.iterator.IteratorI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.CollectionUtil;

/**
 * @author wu
 * 
 */
public abstract class IteratorSupport<T> implements IteratorI<T> {

	/* */
	@Override
	public void remove() {
		throw new FsException("TODO");
	}

	/* */
	@Override
	public void forEach(CallbackI<T, Boolean> visitor) {
		for (; this.hasNext();) {
			T n = this.next();
			Boolean stop = visitor.execute(n);
			if (stop != null && stop) {
				break;
			}
		}

	}

	/* */
	@Override
	public T single() {

		return this.single(false);

	}

	/* */
	@Override
	public T single(boolean force) {

		return CollectionUtil.single(this.getAsList(), force);

	}

	/* */
	@Override
	public List<T> getAsList() {
		List<T> rt = new ArrayList<T>();
		while (this.hasNext()) {
			rt.add(this.next());
		}
		return rt;

	}

}
