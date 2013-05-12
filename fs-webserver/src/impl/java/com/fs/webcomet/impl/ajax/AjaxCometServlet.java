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

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.session.SessionI;
import com.fs.commons.api.session.SessionManagerI;
import com.fs.commons.api.session.SessionServerI;
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
 *         <P>
 *         This is the manager of ajax session/comet.
 */
public class AjaxCometServlet extends ConfigurableServletSupport {

	private static final Logger LOG = LoggerFactory.getLogger(AjaxCometServlet.class);

	// NOTE,must same as client.
	public static final String HK_SESSION_ID = "x-fs-ajax-sessionId";

	public static final String SK_COMET = "ajaxComet";

	protected AjaxCometManagerImpl manager;

	protected Map<Path, AjaxMsgHandler> handlers;

	protected AjaxMsgHandler defaultAjaxMsgHandler;

	protected SessionManagerI sessions;

	protected long maxIdleTimeout;// default

	public String getInitParameter(String key, boolean force) {
		String v = this.getInitParameter(key);
		if (v == null && force) {
			throw new FsException("parameter:" + key + " not found for servlet:" + this);
		}
		return v;
	}

	@Override
	public void init() throws ServletException {
		try {
			String bs = getInitParameter("inputBufferSize");
			WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
			if (bs != null) {
				policy.setInputBufferSize(Integer.parseInt(bs));
			}

			{
				String max = getInitParameter("maxIdleTime", true);
				this.maxIdleTimeout = (Integer.parseInt(max));
				LOG.info("maxIdleTime:" + max);//
			}

		} catch (Exception x) {
			throw new ServletException(x);
		}
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);

		//
	}

	/* */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		if (LOG.isDebugEnabled()) {

			String ccode = req.getCharacterEncoding();
			String ctype = req.getContentType();
			if (!"utf-8".equalsIgnoreCase(ccode)) {//
				throw new FsException("only support utf-8");
			}
			int len = req.getContentLength();
			if (len == 0) {//
				throw new FsException("len is zero");
			}
		}

		Reader reader = req.getReader();
		res.setContentType("application/json; charset=UTF-8");
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
			SessionI s = this.sessions.touchSession(sid);
			if (s == null) {// session is missing,
				//
			} else {
				as = (AjaxComet) s.getProperty(SK_COMET, true);
			}
		}

		// NOTE, the timeout for first message should be long enough,
		//
		// it's rely on the client's applevel to keep the connection open, then
		// it will send applevel's heartbeat.
		//
		AjaxRequestContext arc = new AjaxRequestContext((int) this.maxIdleTimeout, (int) this.maxIdleTimeout,
				as, req, res);
		// NOTE write to response will cause the EOF of the reader?
		if (as != null) {
			as.startRequest(arc);// may blocking if has old request .
		}
		try {

			arc.writeMessageStart();
			try {
				this.doRequest(sid, req, arc);
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

	protected void doRequest(String sid, HttpServletRequest req, AjaxRequestContext arc)
			throws ServletException, IOException {
		// virtual terminal id
		if (sid != null && arc.as == null) {// missing session
			arc.writeError(AjaxMsg.ERROR_CODE_SESSION_NOTFOUND, "yes,not found!");
			return;
		}

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

		// NOTE,init have not yet called for now.

		SessionServerI ss = this.container.find(SessionServerI.class, true);
		this.sessions = ss.createManager("ajax-servelt-for-manager-" + name);

		AjaxCometManagerImpl rt = new AjaxCometManagerImpl(ap, name);
		this.manager = rt;
		this.handlers = new HashMap<Path, AjaxMsgHandler>();
		// default handler
		this.defaultAjaxMsgHandler = new AjaxDefaultHandler(this.sessions, this.manager);
		// handlers
		this.handlers.put(AjaxMsg.CLOSE, new AjaxCloseHandler(this.sessions, this.manager));
		this.handlers.put(AjaxMsg.CONNECT, new AjaxConnectHandler(this.sessions, this.manager));
		this.handlers.put(AjaxMsg.MESSAGE, new AjaxMessageHandler(this.sessions, this.manager));
		this.handlers.put(AjaxMsg.HEART_BEAT, new AjaxHeartBeatHandler(this.sessions, this.manager));
		//
		return rt;

	}
}
