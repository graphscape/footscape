/**
 * Jul 22, 2012
 */
package com.fs.commons.api.validator;

/**
 * @author wu
 * 
 */
public interface ConditionI<T> {

	public boolean isMeet(T cxt);

}
