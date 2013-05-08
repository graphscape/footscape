/**
 *  
 */
package com.fs.webcomet.api;

import com.fs.commons.api.ActiveContext;

/**
 * @author wu
 * 
 */
public interface CometProtocolI {

	public CometManagerI createManager(ActiveContext ac, String name);

	/**
	 * @return
	 */
	public String getName();

}
