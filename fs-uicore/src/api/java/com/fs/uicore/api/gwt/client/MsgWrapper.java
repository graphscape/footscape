/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wuzhen
 * 
 */
public class MsgWrapper {

	protected MessageData target;

	public MsgWrapper(Path path) {
		this(new MessageData(path));
	}

	public MsgWrapper(MessageData md) {
		this.target = md;
	}

	public String getHeader(String key, boolean force) {
		return this.target.getHeader(key, force);
	}

	public String getHeader(String key) {
		return this.target.getHeader(key);
	}

	/**
	 * @param headers
	 *            the header to set
	 */
	public void setHeader(String key, String value) {
		this.target.setHeader(key, value);
	}

	/**
	 * @return the payload
	 */
	public ObjectPropertiesData getPayloads() {
		return this.target.getPayloads();
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayloads(ObjectPropertiesData pts) {
		this.target.setPayloads(pts);
	}

	public <T> T getPayload(String key, boolean force) {
		Object rt = this.getPayloads().getProperty(key, force);

		return (T) rt;
	}

	public void setPayload(String key, boolean value) {
		this.target.setPayload(key, value);
	}

	public void setPayload(String key, String value) {
		
		this.target.setPayload(key, (value));
		
	}

	public void setPayload(String key, Object value) {
		this.target.setPayload(key, value);
	}

	public String getPayLoadAsString(String key, boolean force) {
		String sd = this.getPayload(key, force);
		String rt = sd == null ? null : sd;
		if (rt == null && force) {
			throw new UiException("no payload:" + key);
		}
		return rt;

	}

	public Boolean getPayLoadAsBoolean(String key, Boolean def) {
		Boolean rt = this.getPayLoadAsBoolean(key, false);
		return rt == null ? def : rt;
	}

	public Boolean getPayLoadAsBoolean(String key, boolean force) {
		Boolean sd = this.getPayload(key, false);
		Boolean rt = sd == null ? null : sd;

		if (rt == null && force) {
			throw new UiException("no payload:" + key);
		}

		return rt;

	}

	/**
	 * @return the target
	 */
	public MessageData getTarget() {
		return target;
	}

}
