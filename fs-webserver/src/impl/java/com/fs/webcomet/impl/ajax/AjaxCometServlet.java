/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fs.commons.api.ActiveContext;
import com.fs.webcomet.api.support.CollectionCometListener;
import com.fs.webserver.api.support.ConfigurableServletSupport;

/**
 * @author wu
 * 
 */
public class AjaxCometServlet extends ConfigurableServletSupport {

	public static final String HK_ACTION = "x-ajax-commet-action";

	public static final String HK_SESSION_ID = "x-ajax-commet-session-id";

	protected Map<String, AjaxComet> sessionMap;

	protected AjaxCometManagerImpl manager;

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.sessionMap = new HashMap<String, AjaxComet>();

		//
	}

	/* */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		// virtual terminal id
		String action = req.getHeader(HK_ACTION);

		if (action == null) {

			return;// ignore
		}

		if (action.equals("connect")) {
			// do connection
			String tid = UUID.randomUUID().toString();
			AjaxComet as = new AjaxComet(tid);
			this.sessionMap.put(tid, as);//
			this.manager.onConnect(as);
			res.setHeader(HK_SESSION_ID, tid);
			return;
		}

		String sid = req.getHeader(HK_SESSION_ID);

		if (sid == null) {

			return;// report error ?
		}

		AjaxComet as = this.sessionMap.get(sid);

		if (action.equals("close")) {
			this.manager.onClose(as, 0, "");
			return;//
		}

		if (action.equals("message")) {

			Reader rd = req.getReader();
			// note, is string array:["abc","def]
			JSONArray jsa = (JSONArray) JSONValue.parse(rd);
			for (int i = 0; i < jsa.size(); i++) {
				String msg = (String) jsa.get(i);
				this.manager.onMessage(as, msg);
			}
			//

		}
		as.fetchMessages(req, res);

	}

	/**
	 * @param ajaxCometProtocol
	 * @param name
	 * @return
	 */
	public AjaxCometManagerImpl attachManager(AjaxCometProtocol ap, String name) {
		AjaxCometManagerImpl rt = new AjaxCometManagerImpl(ap, name);
		this.manager = rt;
		return rt;

	}

}
