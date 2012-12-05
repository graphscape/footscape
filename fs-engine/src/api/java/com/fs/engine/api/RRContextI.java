/**
 * Jun 14, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.context.ContextI;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public interface RRContextI extends ContextI {

	public void setHeaders(PropertiesI<String> pts);

	public PropertiesI<String> getHeaders();

	public PropertiesI<Object> getPayloads();

	public void setPayloads(PropertiesI<Object> pts);
	
	public Object getPayload();
	
	public Object getPayload(String key);
	
	public Object getPayload(String key, boolean force);
	
	public <T> T getPayload(Class<T> cls,String key,T def);
	
	public void setPayload(Object pl);
	
	public void setPayload(String key,Object value);

	public String getHeader(String key);
	
	public String getHeader(String key, boolean force);

	public String getHeader(String key, String def);

	public void setHeader(String key, String value);
}
