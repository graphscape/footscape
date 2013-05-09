/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.fs.uicore.impl.gwt.client.comet;

/**
 * @author wu
 * 
 */
public abstract class GometSupport implements GometI {

	protected String protocol;

	public GometSupport(String pro) {
		this.protocol = pro;
	}

	public String getProtocol() {
		return this.protocol;
	}

}
