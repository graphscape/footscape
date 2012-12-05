/**
 * Jun 10, 2012
 */
package com.fs.commons.api.callback;

/**
 * @author wu
 * 
 */
public interface CallbackI<IT, RT> {
	public RT execute(IT i);
}
