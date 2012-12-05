/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package com.fs.commons.api.message.support;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public class MessageSupport implements MessageI {
	private PropertiesI<String> headers;
	private PropertiesI<Object> payloads;

	public MessageSupport() {
		this.headers = new MapProperties<String>();
		this.payloads = new MapProperties<Object>();

	}

	/* */
	@Override
	public PropertiesI<String> getHeaders() {

		return this.headers;

	}

	/* */
	@Override
	public PropertiesI<Object> getPayloads() {

		return this.payloads;

	}

	@Override
	public String getHeader(String key) {
		//
		return this.headers.getProperty(key);
		//
	}

	@Override
	public void setHeader(String key, String value) {
		//
		this.headers.setProperty(key, value);//
	}

	@Override
	public Object getPayload(String key) {
		//
		return this.payloads.getProperty(key);
		//
	}

	@Override
	public void setPayload(String key, Object value) {
		//
		this.payloads.setProperty(key, value);
		//
	}

	/*
	
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof MessageI)) {
			return false;
		}
		MessageI m = (MessageI) obj;
		if (!m.getHeaders().equals(this.headers)) {
			return false;
		}
		if (!this.payloads.equals(m.getPayloads())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("headers:");
		sb.append(this.headers.toString());
		sb.append("payloads:");
		sb.append(this.payloads.toString());
		return sb.toString();
	}

}
