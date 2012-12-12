/**
 *  Dec 11, 2012
 */
package com.fs.webserver.impl.jetty.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * @see WebSocketServlet <p>
 *      This class should extends that class,but the the base
 *      WebSocketServletFactory class cannot be load there.
 * 
 */

public class JettyWsServletImpl extends HttpServlet {

	protected JettyWsManagerImpl manager;

	private WebSocketServletFactory factory;

	public JettyWsServletImpl() {

	}

	@Override
	public void destroy() {
		factory.cleanup();
	}

	@Override
	public void init() throws ServletException {
		try {
			String bs = getInitParameter("bufferSize");
			WebSocketPolicy policy = new WebSocketPolicy(
					WebSocketBehavior.SERVER);
			if (bs != null) {
				policy.setBufferSize(Integer.parseInt(bs));
			}

			String max = getInitParameter("maxIdleTime");
			if (max != null) {
				policy.setIdleTimeout(Integer.parseInt(max));
			}

			max = getInitParameter("maxTextMessageSize");
			if (max != null) {
				policy.setMaxTextMessageSize(Integer.parseInt(max));
			}

			max = getInitParameter("maxBinaryMessageSize");
			if (max != null) {
				policy.setMaxBinaryMessageSize(Integer.parseInt(max));
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
		ClassLoader loader = this.getServletContext().getClass()
				.getClassLoader();

		WebSocketServletFactory baseFactory;
		Iterator<WebSocketServletFactory> factories = ServiceLoader.load(
				WebSocketServletFactory.class, loader).iterator();

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
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (factory.isUpgradeRequest(request, response)) {
			// We have an upgrade request
			if (factory.acceptWebSocket(request, response)) {
				// We have a socket instance created
				return;
			}
			// If we reach this point, it means we had an incoming request to
			// upgrade
			// but it was either not a proper websocket upgrade, or it was
			// possibly rejected
			// due to incoming request constraints (controlled by
			// WebSocketCreator)
			if (response.isCommitted()) {
				// not much we can do at this point.
				return;
			}
		}

		// All other processing
		super.service(request, response);
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
	public JettyWsManagerImpl attachManager(String name) {
		this.manager = new JettyWsManagerImpl(name);
		return this.manager;
	}
}