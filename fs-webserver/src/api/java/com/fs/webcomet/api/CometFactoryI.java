/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webcomet.api;

import com.fs.commons.api.ActiveContext;

/**
 * @author wu
 * 
 */
public interface CometFactoryI {

	public void addProtocol(CometProtocolI cp);

	public CometProtocolI getProtocol(String name, boolean force);

	public CometManagerI addManager(ActiveContext ac, String protocol, String name);

	public CometManagerI getManager(String protocol, String name, boolean force);

}
