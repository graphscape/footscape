/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.UiData;
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

}
