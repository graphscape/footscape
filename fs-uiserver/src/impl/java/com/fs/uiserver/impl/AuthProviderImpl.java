/**
 *  Dec 19, 2012
 */
package com.fs.uiserver.impl;

import com.fs.commons.api.lang.FsException;
import com.fs.gridservice.commons.api.session.AuthProviderI;

/**
 * @author wuzhen
 * 
 */
public class AuthProviderImpl implements AuthProviderI {

	@Override
	public boolean auth(String accId, String pass) {
		throw new FsException("TODO, auth from gridservice");
	}

}
