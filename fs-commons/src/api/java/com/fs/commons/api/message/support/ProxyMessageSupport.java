/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package com.fs.commons.api.message.support;

import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.ErrorInfos;
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

	/*
	 * Dec 15, 2012
	 */
	@Override
	public String getHeader(String key, boolean force) {
		return this.target.getHeader(key, force);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public String getHeader(String key, String def) {
		//
		return this.target.getHeader(key, def);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public Object getPayload() {
		return this.target.getPayload();
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public Object getPayload(String key, boolean force) {
		return this.target.getPayload(key, force);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public <T> T getPayload(Class<T> cls, String key, T def) {
		return this.target.getPayload(cls, key, def);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setPayload(Object pl) {
		this.target.setPayload(pl);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setPayloads(PropertiesI<Object> pls) {
		this.target.setPayloads(pls);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setMessage(MessageI msg) {
		this.target.setMessage(msg);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void setHeaders(PropertiesI<String> hds) {
		this.target.setHeaders(hds);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ProxyMessageSupport)) {
			return false;
		}

		return ObjectUtil.nullSafeEquals(this.target, ((ProxyMessageSupport) obj).target);

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public Object getPayload(String key, Object def) {
		//
		return this.target.getPayload(key, def);
	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key) {
		//
		return this.target.getString(key);

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key, boolean force) {
		//
		return this.target.getString(key, force);

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public String getString(String key, String def) {
		return this.target.getString(key, def);
	}

	@Override
	public String getErrorProcessor() {
		return this.target.getErrorProcessor();
	}

	@Override
	public String getSourceId() {
		return this.target.getSourceId();
	}

	@Override
	public Path getPath() {
		return this.target.getPath();
	}

	@Override
	public String getId() {
		return this.target.getId();
	}

	@Override
	public String getResponseAddress() {
		return this.target.getResponseAddress();
	}

	@Override
	public boolean getBoolean(String key, boolean def) {
		return this.target.getBoolean(key, def);
	}

	/*
	 * Jan 29, 2013
	 */
	@Override
	public boolean isSilence() {
		//
		return this.target.isSilence();
	}

	/*
	 *Apr 4, 2013
	 */
	@Override
	public ErrorInfos getErrorInfos() {
		// 
		return this.target.getErrorInfos();
	}

	/*
	 *Apr 4, 2013
	 */
	@Override
	public void assertNoError() {
		this.target.assertNoError();
	}

}
