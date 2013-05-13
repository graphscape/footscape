/**
 *  
 */
package com.fs.uicore.impl.gwt.client.comet.ajax;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * @author wu
 * 
 */
public class AjaxMsg extends MapProperties<String> {

	public static final Path CONNECT = Path.valueOf("ajax/connect");

	public static final Path CLOSE = Path.valueOf("ajax/close");

	public static final Path MESSAGE = Path.valueOf("ajax/message");

	public static final Path ERROR = Path.valueOf("ajax/error");

	public static final Path HEART_BEAT = Path.valueOf("ajax/heart-beat");

	public static final String PK_PATH = "_path";

	public static final String PK_TIMESTAMP = "_timestamp";

	public static final String PK_SESSION_ID = "_session_id";

	public static final String PK_TEXTMESSAGE = "_text_message";

	public static final String PK_ERROR_CODE = "_error_code";

	public static final String PK_ERROR_MSG = "_error_message";

	public static final String ERROR_CODE_SESSION_NOTFOUND = "session-not-found";

	public AjaxMsg(JSONObject jo) {
		for (String k : jo.keySet()) {
			JSONString sJ = (JSONString) jo.get(k);
			String v = sJ.stringValue();
			this.setProperty(k, v);
		}

	}

	public AjaxMsg(Path path) {
		this(path, null);
	}

	public AjaxMsg(Path path, String sid) {
		this.setProperty(PK_PATH, path.toString());
		if (sid != null) {
			this.setProperty(PK_SESSION_ID, sid);
		}
		long now = System.currentTimeMillis();
		this.setProperty(PK_TIMESTAMP, now + "");
	}

	public boolean isPath(Path path) {
		return path.equals(this.getPath());
	}

	public String getSessionId(boolean force) {
		return this.getProperty(PK_SESSION_ID, force);
	}

	public Path getPath() {
		String ps = this.getProperty(PK_PATH, true);
		return Path.valueOf(ps);
	}

	public JSONObject getAsJsonObject() {
		JSONObject rt = new JSONObject();
		for (String key : this.keyList()) {
			String value = this.getProperty(key);
			JSONString jsonValue = new JSONString(value);
			rt.put(key, jsonValue);
		}
		return rt;
	}

	/**
	 * May 8, 2013
	 */
	public static List<AjaxMsg> tryParseMsgArray(JSONArray jsa) {
		//
		List<AjaxMsg> rt = new ArrayList<AjaxMsg>();
		for (int i = 0; i < jsa.size(); i++) {

			JSONObject jo = (JSONObject) jsa.get(i);
			/*
			 * JSONString pathJO = (JSONString)jo.get(PK_PATH); String pathS =
			 * (String) pathJO.stringValue();
			 */
			AjaxMsg am = new AjaxMsg(jo);

			rt.add(am);
		}
		return rt;
	}

}