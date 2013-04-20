/**
 * Jun 15, 2012
 */
package com.fs.commons.api.service;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.filter.ChainI;

/**
 * @author wuzhen
 * 
 */
public interface ServiceI<R, T, C extends ServiceContext<R, T>> {

	public void service(R req, CallbackI<T, Object> res);

	public void service(R req, T res);

	public T service(R req);
	
	public ChainI<R,T> getChain();

	public DispatcherI<C> getDispatcher();

}
