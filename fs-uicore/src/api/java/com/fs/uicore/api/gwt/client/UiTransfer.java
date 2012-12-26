/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wuzhen
 * 
 */
public class UiTransfer extends UiObjectSupport {

	private UiHeaders headers;

	private ObjectPropertiesData payloads;

	public UiTransfer(String name) {
		super(name);
		this.headers = new UiHeaders();
		this.payloads = new ObjectPropertiesData();
	}

	/**
	 * @return the header
	 */
	public UiHeaders getHeaders() {
		return headers;
	}

	public String getHeader(String key) {
		return this.headers.getProperty(key);
	}

	/**
	 * @param headers
	 *            the header to set
	 */
	public void setHeader(String key, String value) {
		this.headers.setProperty(key, value);
	}

	/**
	 * @return the payload
	 */
	public ObjectPropertiesData getPayloads() {
		return payloads;
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayloads(ObjectPropertiesData pts) {
		this.payloads.setProperties(pts);
	}

	public <T extends UiData> T getPayload(String key, boolean force) {
		Object rt = this.getPayloads().getProperty(key, force);

		return (T) rt;
	}

	public void setPayload(String key, UiData value) {
		this.payloads.setProperty(key, value);
	}

	public String getPayLoadAsString(String key, boolean force) {
		StringData sd = this.getPayload(key, force);
		String rt = sd == null ? null : sd.getValue();
		if (rt == null && force) {
			throw new UiException("no payload:" + key);
		}
		return rt;

	}

	public Boolean getPayLoadAsBoolean(String key, Boolean def) {
		Boolean rt = this.getPayLoadAsBoolean(key, false);
		return rt == null ? def : rt;
	}

	@Override
	public String toDebugString() {
		String rt = super.toDebugString();
		rt += "\nheaders:" + this.headers;
		rt += "\npayloads:" + this.payloads;

		return rt;

	}

	public Boolean getPayLoadAsBoolean(String key, boolean force) {
		BooleanData sd = this.getPayload(key, false);
		Boolean rt = sd == null ? null : sd.getValue();

		if (rt == null && force) {
			throw new UiException("no payload:" + key);
		}

		return rt;

	}

}
