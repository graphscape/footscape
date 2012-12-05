/**
 * Jun 18, 2012
 */
package com.fs.commons.api.wrapper;

import java.util.Iterator;

import com.fs.commons.api.iterator.IteratorI;
import com.fs.commons.api.support.IteratorSupport;

/**
 * @author wu
 * 
 */
public class IteratorWrapper<T> extends IteratorSupport<T> implements
		IteratorI<T> {

	private Iterator<T> iterator;

	public IteratorWrapper(Iterator<T> it) {
		this.iterator = it;
	}

	public static <R> IteratorWrapper<R> valueOf(Iterator<R> it) {
		return new IteratorWrapper<R>(it);
	}

	/* */
	@Override
	public boolean hasNext() {

		return this.iterator.hasNext();

	}

	/* */
	@Override
	public T next() {

		return this.iterator.next();

	}

	/* */
	@Override
	public void remove() {
		this.iterator.remove();
	}

}
