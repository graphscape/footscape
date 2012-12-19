/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package com.fs.commons.api.message.support;

import com.fs.commons.api.lang.FsException;
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

	public static MessageI newMessage() {
		return new MessageImpl();
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

	/*
	 * Dec 15, 2012
	 */
	@Override
	public String getHeader(String key, boolean force) {
		//
		String rt = this.getHeader(key);
		if (force && rt == null) {
			throw new FsException("no header found:" + key);
		}
		return rt;
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public String getHeader(String key, String def) {

		String rt = this.getHeaders().getProperty(key);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	/* */
	@Override
	public Object getPayload() {

		return this.getPayloads().getProperty(PK_DEFAULT);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public Object getPayload(String key, boolean force) {
		//
		Object rt = this.getPayloads().getProperty(key);
		if (force && rt == null) {
			throw new FsException("force key:" + key);
		}
		return rt;
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public <T> T getPayload(Class<T> cls, String key, T def) {
		//
		Object rt = this.getPayload(key);
		if (rt == null) {
			return def;
		}
		return (T) rt;
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setPayload(Object pl) {
		this.getPayloads().setProperty(PK_DEFAULT, pl);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setPayloads(PropertiesI<Object> pls) {
		this.payloads.setProperties(pls);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setHeaders(PropertiesI<String> hds) {
		this.headers.setProperties(hds);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setMessage(MessageI msg) {
		this.setHeaders(msg.getHeaders());
		this.setPayloads(msg.getPayloads());
	}

	/**
	 * Dec 15, 2012
	 */
	public static MessageI newMessage(PropertiesI<String> hds, PropertiesI<Object> pls) {
		MessageI rt = newMessage();
		rt.setHeaders(hds);
		rt.setPayloads(pls);
		return rt;
	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key) {
		//
		return (String) this.getPayload(key);

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key, boolean force) {
		//
		return (String) this.getPayload(key, force);
	}

	@Override
	public Object getPayload(String key, Object def) {
		Object rt = this.getPayload(key);
		return rt == null ? def : rt;

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key, String def) {
		//
		return (String) this.getPayload(key, def);
	}

}
