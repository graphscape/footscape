/**
 *  Dec 17, 2012
 */
package com.fs.commons.api;

/**
 * @author wuzhen
 * 
 */
public interface InterceptorI {

	public void beforeActive(ActivableI obj);

	public void afterActive(ActivableI obj);

}
