/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.struct.Path;
import com.fs.webcomet.impl.ajax.handlers.AjaxCloseHandler;
import com.fs.webcomet.impl.ajax.handlers.AjaxConnectHandler;
import com.fs.webcomet.impl.ajax.handlers.AjaxDefaultHandler;
import com.fs.webcomet.impl.ajax.handlers.AjaxMessageHandler;
import com.fs.webserver.api.support.ConfigurableServletSupport;

/**
 * @author wu
 * 
 */
public class AjaxCometServlet extends ConfigurableServletSupport {

	protected Map<String, AjaxComet> sessionMap;

	protected AjaxCometManagerImpl manager;

	protected Map<Path, AjaxMsgHandler> handlers;

	protected AjaxMsgHandler defaultAjaxMsgHandler;

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
		AjaxRequestContext arc = new AjaxRequestContext(req, res);
		arc.writeMessageStart();
		try {
			this.doRequest(arc);
			arc.tryFetchMessage();
		} finally {
			arc.writeMessageEnd();
		}

	}

	protected void doRequest(AjaxRequestContext arc) throws ServletException, IOException {
		// virtual terminal id
		Reader reader = arc.req.getReader();
		List<AjaxMsg> amL = AjaxMsg.tryParseMsgArray(reader);

		String firstSid = null;
		int i = 0;
		int total = amL.size();
		for (AjaxMsg am : amL) {

			Path path = am.getPath();
			AjaxMsgHandler hdl = this.handlers.get(path);
			if (hdl == null) {
				hdl = this.defaultAjaxMsgHandler;
			}

			AjaxComet as = null;
			String sid2 = am.getSessionId(false);
			if (i == 0) {
				firstSid = sid2;
				if (sid2 != null) {
					as = this.sessionMap.get(sid2);
					arc.as = as;//

				}
			} else {// must be same sid;
				if (!ObjectUtil.nullSafeEquals(firstSid, sid2)) {
					throw new FsException("sid must be same in same request.");
				}
			}

			AjaxMsgContext amc = new AjaxMsgContext(i, total, am, arc);

			hdl.handle(amc);
			i++;
		}

	}

	/**
	 * @param ajaxCometProtocol
	 * @param name
	 * @return
	 */
	public AjaxCometManagerImpl attachManager(AjaxCometProtocol ap, String name) {
		AjaxCometManagerImpl rt = new AjaxCometManagerImpl(ap, name);
		this.manager = rt;
		this.handlers = new HashMap<Path, AjaxMsgHandler>();
		// default handler
		this.defaultAjaxMsgHandler = new AjaxDefaultHandler(this.sessionMap, this.manager);
		// handlers
		this.handlers.put(AjaxMsg.CLOSE, new AjaxCloseHandler(this.sessionMap, this.manager));
		this.handlers.put(AjaxMsg.CONNECT, new AjaxConnectHandler(this.sessionMap, this.manager));
		this.handlers.put(AjaxMsg.MESSAGE, new AjaxMessageHandler(this.sessionMap, this.manager));

		return rt;

	}
}
