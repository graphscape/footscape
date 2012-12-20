/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.impl.gwt.client;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiFilterI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ErrorEvent;
import com.fs.uicore.api.gwt.client.event.ResponseEvent;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class LastFilter implements UiFilterI {

	private UiClientImpl client;

	public LastFilter(UiClientImpl c) {
		this.client = c;
	}

	@Override
	public void filter(final Context fc, final UiCallbackI<UiResponse, Object> cb) {
		//

		UiRequest req = fc.getRequest();
		if (!req.isInit()) {
			// sessionid
			if (this.client.getSessionId() == null) {
				throw new UiException("sessionId is null,cannot continue,please re init to get a sessionId");
			}
			req.setHeader(UiRequest.SESSION_ID, this.client.getSessionId());
		}

		// if the path is relative,it will be explained to prefix with
		// context path:
		// for instance relative: x/y will be full path: /uiserver/x/y
		// for instance full path: /x/y will not change.
		String path = req.getRequestPath();
		if (!path.startsWith("/")) {// is full path
			//
			path = "/uiserver/" + path;// TODO configurable.
			req.setRequestPath(path);//
		}
		String url = this.client.getUrl();

		Resource res = new Resource(url);
		ObjectPropertiesData ds = req.getPayloads();
		CodecI codec = this.client.getCodecFactory().getCodec(ds.getClass());
		JSONValue json = (JSONValue) codec.encode(ds);//

		Method m = res.post().json(json);

		// String path = req.getPath();
		// m.header(UiRequest.PATH, path);
		for (String key : req.getHeaders().keyList()) {
			String value = req.getHeader(key);
			m.header(key, value);
		}

		JsonCallback jcb = new JsonCallback() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				//
				// TODO
				LastFilter.this.onFailure(exception); //
			}

			@Override
			public void onSuccess(Method method, JSONValue response) {
				//
				LastFilter.this.onSuccess(fc, response, cb);
				//
			}
		};
		m.send(jcb);
		//
	}

	private void onFailure(Throwable t) {
		new ErrorEvent(this.client, t).dispatch();
	}

	private void onSuccess(UiFilterI.Context fc, JSONValue res, UiCallbackI<UiResponse, Object> cb) {

		new ResponseEvent(this.client).dispatch();
		this.client.processResponse(fc.getResponse(), res, cb);
	}

}