/**
 *  Dec 11, 2012
 */
package com.fs.websocket.impl.jetty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;
import com.fs.webcomet.api.CometProtocolI;

/**
 * @author wuzhen
 * @see WebSocketServlet <p>
 *      This class should extends that class,but the the base
 *      WebSocketServletFactory class cannot be load there.
 * 
 */

public class JettyWsServletImpl extends HttpServlet {

	private static Logger LOG = LoggerFactory.getLogger(JettyWsServletImpl.class);

	protected JettyWsManagerImpl manager;

	private WebSocketServletFactory factory;

	public JettyWsServletImpl() {

	}

	@Override
	public void destroy() {
		factory.cleanup();
	}

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

			String max = getInitParameter("maxIdleTime", true);

			policy.setIdleTimeout(Integer.parseInt(max));
			LOG.info("maxIdleTime:" + max);//
			max = getInitParameter("maxMessageSize");
			if (max != null) {
				policy.setMaxMessageSize(Integer.parseInt(max));
			}

			WebSocketServletFactory baseFactory = this.getBaseFactory();
			factory = baseFactory.createFactory(policy);

			configure(factory);

			factory.init();
		} catch (Exception x) {
			throw new ServletException(x);
		}
	}

	protected WebSocketServletFactory getBaseFactory() throws Exception {
		ClassLoader loader = this.getServletContext().getClass().getClassLoader();

		WebSocketServletFactory baseFactory;
		Iterator<WebSocketServletFactory> factories = ServiceLoader.load(WebSocketServletFactory.class,
				loader).iterator();

		if (factories.hasNext())
			baseFactory = factories.next();
		else {
			Class<WebSocketServletFactory> wssf = (Class<WebSocketServletFactory>) loader
					.loadClass("org.eclipse.jetty.websocket.server.WebSocketServerFactory");
			baseFactory = wssf.newInstance();
		}
		return baseFactory;
	}

	/**
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		boolean accept = false;
		boolean isupgrade = factory.isUpgradeRequest(request, response);
		if (isupgrade) {
			accept = factory.acceptWebSocket(request, response);
		}
		String userAgent = request.getHeader("User-Agent");
		LOG.info("accept:" + accept + ",user-agent:" + userAgent);
		if (LOG.isDebugEnabled()) {

			Map<String, List<String>> headers = new HashMap<String, List<String>>();

			for (Enumeration<String> nE = request.getHeaderNames(); nE.hasMoreElements();) {
				String name = nE.nextElement();
				List<String> vL = new ArrayList<String>();
				headers.put(name, vL);
				for (Enumeration<String> vE = request.getHeaders(name); vE.hasMoreElements();) {
					vL.add(vE.nextElement());
				}
			}

			LOG.debug("accept:" + accept + ",headers:" + headers);

		}
	}

	public void configure(WebSocketServletFactory factory) {

		if (this.manager == null) {
			throw new FsException("manager should be set before servlet init");
		}
		manager.configure(factory);
	}

	/**
	 * Dec 11, 2012
	 */
	public JettyWsManagerImpl attachManager(CometProtocolI cp, String name) {
		this.manager = new JettyWsManagerImpl(cp, name);

		return this.manager;
	}
}