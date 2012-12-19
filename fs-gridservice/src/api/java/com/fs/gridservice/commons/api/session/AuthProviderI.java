/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.api.session;

/**
 * @author wuzhen
 * 
 */
public interface AuthProviderI {
	public boolean auth(String accId, String pass);
}
