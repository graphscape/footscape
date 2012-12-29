/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.api.session;

import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public interface AuthProviderI {
	
	public PropertiesI<Object> auth(PropertiesI<Object> credential);
	
}
