/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.core;

/**
 * @author wuzhen
 * 
 */
public interface UiCallbackI<T, RT> {

	public RT execute(T t);

}
