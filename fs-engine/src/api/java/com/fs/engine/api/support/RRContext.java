/**
 * Jun 15, 2012
 */
package com.fs.engine.api.support;

import com.fs.commons.api.context.support.ContextSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.RRContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wuzhen
 * 
 */
public class RRContext extends ContextSupport implements RRContextI {

	public static final String PAYLOAD = "_PAYLOAD";

	public static final String HEADER = "_HEADER";

	private static class RequestImpl extends RRContext implements RequestI {

		/* */
		@Override
		public void setPath(String path) {
			this.setHeader(RequestI.PATH, path);
		}

		/* */
		@Override
		public String getPath() {

			return this.getHeader(RequestI.PATH);

		}

	}

	private static class ResponseImpl extends RRContext implements ResponseI {
		public static final String ERROR_INFO_S = "_ERROR_INFO_S";

		public ResponseImpl() {
			ErrorInfos eis = new ErrorInfos();
			this.setPayload(ERROR_INFO_S, eis);
		}

		/* */
		@Override
		public ErrorInfos getErrorInfos() {

			return (ErrorInfos) this.getPayload(ERROR_INFO_S);

		}

		/*
		 * Nov 3, 2012
		 */
		@Override
		public void assertNoError() {
			ErrorInfos ers = this.getErrorInfos();
			if (ers.hasError()) {
				throw new FsException(ers.toString());

			}
		}

	}

	public static RequestI newRequest() {
		return new RequestImpl();
	}

	public static ResponseI newResponse() {
		return new ResponseImpl();
	}

	public RRContext() {
		this.setProperty(HEADER, new MapProperties<String>());
		this.setProperty(PAYLOAD, new MapProperties<Object>());

	}

	/* */
	@Override
	public PropertiesI<String> getHeaders() {

		PropertiesI<String> rt = (PropertiesI<String>) this.getProperty(HEADER);

		return rt;
	}

	/*
	
	 */
	@Override
	public PropertiesI<Object> getPayloads() {
		PropertiesI<Object> rt = (PropertiesI<Object>) this
				.getProperty(PAYLOAD);
		return rt;
	}

	/*
	
	 */
	@Override
	public void setHeaders(PropertiesI<String> pw) {
		this.getHeaders().setProperties(pw);

	}

	/* */
	@Override
	public String getHeader(String key) {

		return this.getHeaders().getProperty(key);

	}/* */

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
	public void setHeader(String key, String value) {
		this.getHeaders().setProperty(key, value);
	}

	/* */
	@Override
	public void setPayloads(PropertiesI<Object> pts) {
		this.getPayloads().setProperties(pts);
	}

	/* */
	@Override
	public Object getPayload() {

		return this.getPayloads().getProperty("_DEFAULT");

	}

	/* */
	@Override
	public void setPayload(Object pl) {
		this.getPayloads().setProperty("_DEFAULT", pl);
	}

	/* */
	@Override
	public Object getPayload(String key) {

		return this.getPayload(key, false);
	}

	/* */
	@Override
	public void setPayload(String key, Object value) {
		this.getPayloads().setProperty(key, value);
	}

	/*
	 * Nov 3, 2012
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
	 * Nov 3, 2012
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
	 * Nov 3, 2012
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
}
