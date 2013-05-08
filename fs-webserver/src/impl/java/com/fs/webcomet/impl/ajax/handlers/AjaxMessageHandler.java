/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax.handlers;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fs.commons.api.lang.FsException;
import com.fs.webcomet.impl.ajax.AjaxComet;
import com.fs.webcomet.impl.ajax.AjaxCometManagerImpl;
import com.fs.webcomet.impl.ajax.AjaxMsgContext;
import com.fs.webcomet.impl.ajax.AjaxMsgHandler;

/**
 * @author wu
 * 
 */
public class AjaxMessageHandler extends AjaxMsgHandler {

	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxMessageHandler(Map<String, AjaxComet> sessionMap, AjaxCometManagerImpl manager) {
		super(sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		Reader rd = null;
		try {
			rd = amc.req.getReader();
		} catch (IOException e) {
			throw new FsException(e);
		}
		// note, is string array:["abc","def]
		JSONArray jsa = (JSONArray) JSONValue.parse(rd);
		for (int i = 0; i < jsa.size(); i++) {
			String msg = (String) jsa.get(i);
			this.manager.onMessage(amc.as, msg);
		}
		this.fetchMessage(amc);

	}
}
