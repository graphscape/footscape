/**
 * Jun 15, 2012
 */
package com.fs.engine.api.support;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wuzhen
 * 
 */
public class RRContext extends MessageSupport {

	private static class RequestImpl extends RRContext implements RequestI {

	}

	private static class ResponseImpl extends RRContext implements ResponseI {

		private RequestI request;

		public ResponseImpl(RequestI req) {
			this.request = req;
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.fs.engine.api.ResponseI#getRequest()
		 */
		@Override
		public RequestI getRequest() {
			// TODO Auto-generated method stub
			return this.request;
		}

	}

	public static RequestI newRequest(String path) {
		RequestI rt = newRequest();

		rt.setHeader(MessageI.HK_PATH, path);
		return rt;
	}

	public static RequestI newRequest() {
		return new RequestImpl();
	}

	public static ResponseI newResponse(RequestI req) {
		return new ResponseImpl(req);
	}

	public RRContext() {

	}

}
