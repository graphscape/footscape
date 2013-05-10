/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.util.StringUtil;
import com.fs.webcomet.impl.ajax.handlers.AjaxCloseHandler;
import com.fs.webcomet.impl.ajax.handlers.AjaxConnectHandler;
import com.fs.webcomet.impl.ajax.handlers.AjaxDefaultHandler;
import com.fs.webcomet.impl.ajax.handlers.AjaxHeartBeatHandler;
import com.fs.webcomet.impl.ajax.handlers.AjaxMessageHandler;
import com.fs.webserver.api.support.ConfigurableServletSupport;

/**
 * @author wu
 * 
 */
public class AjaxCometServlet extends ConfigurableServletSupport {

	private static final Logger LOG = LoggerFactory.getLogger(AjaxCometServlet.class);

	//NOTE,must same as client.
	public static final String HK_SESSION_ID = "x-fs-ajax-sessionId";

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
		Reader reader = req.getReader();
		// if reader cannot read, check Content-Length
		// http://osdir.com/ml/java.jetty.general/2002-12/msg00198.html
		if (false) {// debug
			String str = StringUtil.readAsString(reader);
			if (LOG.isDebugEnabled()) {
				LOG.debug("request text:" + str);
			}
			reader = new StringReader(str);
		}

		// find the session
		String sid = req.getHeader(HK_SESSION_ID);
		AjaxComet as = null;

		if (sid != null) {// no session before,to establish the new session
			as = this.sessionMap.get(sid);
		}

		AjaxRequestContext arc = new AjaxRequestContext(as, res);
		// NOTE write to response will cause the EOF of the reader?
		if (as != null) {
			as.startRequest(arc);// may blocking if has old request .
		}
		try {

			arc.writeMessageStart();
			try {
				this.doRequest(req, arc);
				arc.tryFetchMessage();
			} finally {
				arc.writeMessageEnd();
			}
		} finally {

			if (as != null) {
				as.endRequest();
			}

		}
	}

	protected void doRequest(HttpServletRequest req, AjaxRequestContext arc) throws ServletException,
			IOException {
		// virtual terminal id
		Reader reader = req.getReader();
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
		this.handlers.put(AjaxMsg.HEART_BEAT, new AjaxHeartBeatHandler(this.sessionMap, this.manager));

		return rt;

	}
}
