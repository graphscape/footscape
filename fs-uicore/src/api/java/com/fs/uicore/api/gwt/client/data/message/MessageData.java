/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.api.gwt.client.data.message;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.data.property.StringPropertiesData;

/**
 * @author wu
 * 
 */
public class MessageData extends UiData {

	private StringPropertiesData headers = new StringPropertiesData();

	private ObjectPropertiesData payloads = new ObjectPropertiesData();

	public MessageData() {

	}

	public MessageData(String path) {
		this.setHeader("path", path);
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
			throw new UiException("no header with key:" + key);
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

	public void setPayload(String key, UiData value) {
		this.payloads.setProperty(key, value);
	}

}
