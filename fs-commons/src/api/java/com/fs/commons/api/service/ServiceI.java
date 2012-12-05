/**
 * Jun 15, 2012
 */
package com.fs.commons.api.service;

import com.fs.commons.api.callback.CallbackI;

/**
 * @author wuzhen
 * 
 */
public interface ServiceI<REQ, RES> {

	public void service(REQ req, CallbackI<RES, Object> res);

	public void service(REQ req, RES res);

	public RES service(REQ req);

}
