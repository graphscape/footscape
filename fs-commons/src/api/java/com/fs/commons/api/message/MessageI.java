/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package com.fs.commons.api.message;

import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public interface MessageI {

	public PropertiesI<String> getHeaders();

	public PropertiesI<Object> getPayloads();

	public String getHeader(String key);

	public void setHeader(String key, String value);

	public Object getPayload(String key);

	public void setPayload(String key, Object value);
}
