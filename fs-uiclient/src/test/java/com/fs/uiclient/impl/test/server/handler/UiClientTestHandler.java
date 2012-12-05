/**
 * Jun 24, 2012
 */
package com.fs.uiclient.impl.test.server.handler;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class UiClientTestHandler extends HandlerSupport {
	private static class ReqWrapper {
		RequestI request;
		private String handler;
		private String method;
		private String caseId;

		public ReqWrapper(RequestI req) {
			this.request = req;
			String path = req.getPath();
			String[] ps = path.split("/");
			handler = ps[2];
			method = ps[3];
			caseId = req
					.getHeader(com.fs.uiclient.impl.gwt.client.Constants.X_FS_TEST_CASE);
		}
	}

	@Override
	public void handle(HandleContextI sc) {
		ReqWrapper req = new ReqWrapper(sc.getRequest());
		// assert equals
		this.assertRequestEquals(req);

		// return response
		PropertiesI res = this.loadData(req, "response");
		sc.getResponse().setPayloads(res);//

	}

	protected void assertRequestEquals(ReqWrapper req) {

		String caseId = req.request.getHeader("TEST_CASE");
		PropertiesI expected = this.loadData(req, "request");
		PropertiesI actual = req.request.getPayloads();

		if (!expected.equals(actual)) {

			throw new FsException("expected:\n" + encode(expected) + ",actual:\n"
					+ encode(actual));
		}
	}

	protected PropertiesI loadData(ReqWrapper req, String rr) {
		String res = "uitest/" + req.handler + "/" + req.method + "."
				+ req.caseId + "." + rr + ".json";
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(res);

		if (is == null) {
			throw new FsException(
					"res not found:"
							+ res
							+ ","
							+ "uitest/<handler>/<method>/<caseid>.<request/response>.json");//
		}
		InputStreamReader rd = new InputStreamReader(is);
		Object obj = JSONValue.parse(rd);
		JSONArray l = (JSONArray) obj;
		CodecI.FactoryI cf = this.container.find(CodecI.FactoryI.class);

		CodecI cd = cf.getCodec(PropertiesI.class);
		PropertiesI uid = (PropertiesI) cd.decode(l);//
		PropertiesI rt = (PropertiesI) uid;

		return rt;
	}

	protected String encode(PropertiesI opd) {
		CodecI.FactoryI cf = this.container.find(CodecI.FactoryI.class);

		CodecI cd = cf.getCodec(PropertiesI.class);
		JSONArray json = (JSONArray) cd.encode(opd);
		return json.toJSONString();
	}

}
