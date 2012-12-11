/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl.jetty;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.ServerSupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;
import com.fs.webserver.impl.util.DateUtil;

/**
 * @author wu
 *         <p>
 *         http://download.eclipse.org/jetty/stable-9/xref/
 */
// 9.0:http://www.eclipse.org/jetty/documentation/current/quick-start-configure.html

// 6.1: http://docs.codehaus.org/display/JETTY/Embedding+Jetty
public class JettyWebServerImpl extends ServerSupport implements WebServerI {
	private static final Logger LOG = LoggerFactory
			.getLogger(JettyWebServerImpl.class);

	private Server server;

	private ContainerI internal;

	private boolean backup = true;

	private File home;

	private int port;
	
	private HandlerCollection handlers;

	@Override
	public void doStart() {
		LOG.info("starting web server at:" + this.home + " with port:"
				+ this.port);
		try {
			this.server.start();

		} catch (Exception e) {
			throw FsException.toRtE(e);//
		}

		/*
		 * try { this.server.join(); } catch (InterruptedException e) { throw
		 * new FsException(e); }
		 */
	}

	/* */
	@Override
	public void cmd(String cmd) {
	}

	/* */
	@Override
	public void shutdown() {
		this.server.destroy();
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		String home = this.config.getProperty("home", false);

		if (home == null) {// create a temp dir
			String uhome = System.getProperty("user.home");
			home = uhome + File.separator + ".fs" + File.separator
					+ "webserver";// TODO
		}
		this.home = new File(home);

		if (this.home.exists()) {
			if (!this.home.isDirectory()) {
				throw new FsException("home:" + this.home
						+ " is not a directory.");
			}
			// backup the history running of web server

			this.tryBackup();

		}

		this.home.mkdirs();

		this.internal = ac.getContainer().find(ContainerI.FactoryI.class, true)
				.newContainer();//
		this.port = this.config.getPropertyAsInt("port", 8080);//
		this.server = new Server(port);
		this.handlers = new HandlerList();
		this.server.setHandler(this.handlers);
		// not start here

	}

	private void tryBackup() {
		if (!this.backup) {
			LOG.info("Web server working directory not backup.");
			return;
		}
		LOG.info("Web server working directory backing up.");

		String fname = DateUtil.getNowFormated();
		File his = new File(this.home.getAbsoluteFile() + fname);
		int idx = 0;
		while (his.exists()) {
			his = new File(this.home.getAbsoluteFile() + fname + "-" + idx);
			idx++;
		}
		boolean rn = this.home.renameTo(his);
		if (!rn) {
			throw new FsException("backup history failed:"
					+ his.getAbsolutePath());
		}
	}

	public File getHome() {
		return this.home;
	}

	/* */
	@Override
	public WebAppI addWebApp(ActiveContext ac, String name, String cfgId) {
		if (this.started || this.starting) {
			throw new FsException("not supported while server is running");
		}

		JettWebAppImpl wai = new JettWebAppImpl(this);
		ac.activitor().context(ac).container(this.internal).object(wai)
				.name(name).cfgId(cfgId).active();
		WebAppContext wac = wai.getJettyWebApp();
		// wac.get
		// this.server.addHandler(wac);//jetty 6
		
		this.handlers.addHandler(wac);//
		//this.handlers.start();
		// this.server.addBean(wac);// TODO test form jetty6 to 9.0
		/**
		 * <code>
		try {
			this.server.getHandler().start();
			// NOTE this handler will be a collection handler when the second
			// handler added to the server,
			// and then this new handler should be start,other wise the server
			// see it not started and return 404 NOT FOUND error.
		} catch (Exception e1) {
			throw new FsException();
		}// NOTE this is
		try {
			wac.start();
		} catch (Exception e) {
			throw new FsException(e);
		}</code>
		 */
		LOG.info("addWebApp,contextPath:" + wai.getContextPath() + ",spi:"
				+ ac.getSpi());
		return wai;
	}

	/* */
	@Override
	public WebAppI getWebApp(String name) {

		return this.internal.finder(WebAppI.class).name(name).find(true);

	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	protected void doShutdown() {
		//
		// TODO
	}

}
