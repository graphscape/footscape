/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.event;

import com.fs.commons.api.value.PropertiesI;
import com.fs.datagrid.api.wrapper.PropertiesDW;
import com.fs.expector.api.event.EndpointEventI;

/**
 * @author wuzhen
 *
 */
public class EndpointEventImpl extends PropertiesDW implements EndpointEventI {

	/* (non-Javadoc)
	 * @see com.fs.commons.api.message.MessageI#getHeaders()
	 */
	@Override
	public PropertiesI<String> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.message.MessageI#getPayloads()
	 */
	@Override
	public PropertiesI<Object> getPayloads() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.message.MessageI#getHeader(java.lang.String)
	 */
	@Override
	public String getHeader(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.message.MessageI#setHeader(java.lang.String, java.lang.String)
	 */
	@Override
	public void setHeader(String key, String value) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.message.MessageI#getPayload(java.lang.String)
	 */
	@Override
	public Object getPayload(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.api.message.MessageI#setPayload(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setPayload(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
