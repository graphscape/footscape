/**
 * Jun 20, 2012
 */
package com.fs.commons.api.support;

import java.util.List;

import com.fs.commons.api.FinderI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.util.CollectionUtil;

/**
 * @author wu
 * 
 */
public abstract class ObjectFinderSupport<T> extends
		DescribedSupport<FinderI<T>> implements FinderI<T> {

	protected boolean withParent;

	/* */
	@Override
	public T find(boolean force) {
		List<T> rt = this.find();

		return CollectionUtil.single(rt, force, this);

	}

	@Override
	public FinderI<T> withParent(boolean wp) {
		this.withParent = wp;
		return this;
	}

	/* */
	@Override
	public void forEach(CallbackI<T, Boolean> cb) {
		for (T t : this.find()) {
			Boolean b = cb.execute(t);
			if (b != null && b) {
				break;
			}
		}

	}

}
