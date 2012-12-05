/**
 * May 31, 2012
 */
package com.fs.commons.api.wrapper;

/**
 * @author wu
 * 
 */
public class Holder<T> {

	private T target;

	public Holder(T t) {
		this.target = t;
	}

	/**
	 * @return the target
	 */
	public T getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(T target) {
		this.target = target;
	}
}
