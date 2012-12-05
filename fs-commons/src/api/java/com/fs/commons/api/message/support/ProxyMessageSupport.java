/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package com.fs.commons.api.message.support;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public class ProxyMessageSupport implements MessageI {

	protected MessageI target;

	public ProxyMessageSupport(MessageI target) {
		this.target = target;
	}

	@Override
	public PropertiesI<String> getHeaders() {
		//
		return this.target.getHeaders();
		//
	}

	@Override
	public PropertiesI<Object> getPayloads() {
		//
		return this.target.getPayloads();
		//
	}

	@Override
	public String getHeader(String key) {
		//
		return this.target.getHeader(key);
		//
	}

	@Override
	public void setHeader(String key, String value) {
		//
		this.target.setHeader(key, value);
		//
	}

	@Override
	public Object getPayload(String key) {
		//
		return this.target.getPayload(key);
		//
	}

	@Override
	public void setPayload(String key, Object value) {
		//
		this.target.setPayload(key, value);
		//
	}

	@Override
	public String toString() {
		return this.target.toString();
	}
}
