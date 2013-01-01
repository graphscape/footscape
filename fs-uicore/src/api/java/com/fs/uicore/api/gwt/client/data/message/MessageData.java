/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.api.gwt.client.data.message;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.data.property.StringPropertiesData;

/**
 * @author wu
 * 
 */
public class MessageData extends UiData {

	public static final String HK_PATH = "_path";
	private StringPropertiesData headers = new StringPropertiesData();

	private ObjectPropertiesData payloads = new ObjectPropertiesData();

	public MessageData() {

	}

	public MessageData(String path) {
		this.setHeader(HK_PATH, path);
	}

	/**
	 * @param path
	 */
	public MessageData(Path path) {
		this(path.toString());
	}

	public StringPropertiesData getHeaders() {
		return headers;
	}

	public String getHeader(String key) {
		String rt = this.headers.getProperty(key, false);
		return rt == null ? null : rt;
	}

	public String getHeader(String key, boolean force) {
		String rt = this.getHeader(key);

		if (force && rt == null) {
			throw new UiException("no header with key:" + key + ",msg:" + this);
		}

		return rt;
	}

	public void setHeader(String key, String value) {
		this.headers.setProperty(key, value);
	}

	public String getString(String key, boolean force) {
		return this.payloads.getString(key, force);
	}

	public String getString(String key) {
		return this.payloads.getString(key);
	}

	public UiData getPayload(String key) {
		UiData rt = this.payloads.getProperty(key, false);
		return rt == null ? null : rt;
	}

	public UiData getPayload(String key, boolean force) {
		UiData rt = this.getPayload(key);

		if (force && rt == null) {
			throw new UiException("no header with key:" + key);
		}

		return rt;
	}

	public ObjectPropertiesData getPayloads() {
		return this.payloads;
	}

	public void setPayloads(PropertiesData<UiData> pts) {
		this.payloads.setProperties(pts);
	}

	public void setPayload(String key, boolean value) {
		this.setPayload(key, BooleanData.valueOf(value));
	}

	public void setPayload(String key, String value) {
		this.setPayload(key, StringData.valueOf(value));
	}

	public void setPayload(String key, UiData value) {
		this.payloads.setProperty(key, value);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ",headers:" + this.headers.toString() + ",payloads:"
				+ this.payloads;
	}

	/**
	 * Jan 1, 2013
	 */
	public Path getPath() {
		//
		String ps = this.getHeader(HK_PATH);
		return Path.valueOf(ps);

	}

}
