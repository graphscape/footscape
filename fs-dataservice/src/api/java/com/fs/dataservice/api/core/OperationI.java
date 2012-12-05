/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core;

import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public interface OperationI<O extends OperationI<O, T>, T extends ResultI<T, ?>> {

	public void addBeforeInterceptor(InterceptorI<O> itr);

	public O beforeInterceptor(InterceptorI<O> itr);
	
	public O prepare();

	public O parameter(String key, Object value);

	public O parameters(PropertiesI<Object> pts);

	public Object getParameter(String key);

	public Object getParameter(String key, boolean force);

	public <X> X getParameter(Class<X> cls, String key, X def);

	public O execute();

	public T getResult();

	public <X> X cast();

}
