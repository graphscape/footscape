/**
 * Jun 19, 2012
 */
package com.fs.commons.api;

/**
 * @author wu
 * 
 */
public interface ActivableI {

	public void active(ActiveContext ac);

	public void deactive(ActiveContext ac);
}
