/**
 * Jun 15, 2012
 */
package com.fs.engine.api.support;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wuzhen
 * 
 */
public class RRContext extends MessageSupport {

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

	}

}
