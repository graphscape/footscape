/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.api.session;

import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public interface AuthProviderI {

	public void auth(PropertiesI<Object> credential, ErrorInfos eis, PropertiesI<Object> ok);

}
