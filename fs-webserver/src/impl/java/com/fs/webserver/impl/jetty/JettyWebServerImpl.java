/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl.jetty;

import java.io.File;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.server.ServerI;
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
	private static final Logger LOG = LoggerFactory.getLogger(JettyWebServerImpl.class);

	private Server server;

	private ContainerI internal;

	private boolean backup = true;

	private File home;

	private int port;

	private boolean sslEnable;

	private int sslPort;

	private String keyStoreType;

	private String keyStoreLocation;

	private String keyStorePassword;

	private JettyWebAppImpl root;

	private HandlerCollection handlers;

	@Override
	public void doStart() {
		LOG.info("starting web server at:" + this.home + " with port:" + this.port);
		if (this.root != null) {
			this.handlers.addHandler(this.root.getJettyWebApp());
		}
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

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		String home = this.config.getProperty("home", false);

		String uhome = System.getProperty("user.home");

		if (home == null) {// create a temp dir
			home = uhome + File.separator + ".fs" + File.separator + "webserver";// TODO
		}
		this.home = new File(home);

		this.port = this.config.getPropertyAsInt("port", 8080);//
		this.sslEnable = this.config.getPropertyAsBoolean("isSslEnable", false);
		if (this.sslEnable) {
			Configuration sslC = Configuration.properties(this.configId + ".ssl");

			String ksl = sslC.getProperty("keyStoreLocation");
			this.keyStoreLocation = ksl.replaceAll("//", File.separator).replace("{user.home}", uhome);

			this.keyStorePassword = sslC.getProperty("keyStorePassword");
			this.keyStoreType = sslC.getProperty("keyStoreType");
			this.sslPort = sslC.getPropertyAsInt("port", 443);//
		}

	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		if (this.home.exists()) {
			if (!this.home.isDirectory()) {
				throw new FsException("home:" + this.home + " is not a directory.");
			}
			// backup the history running of web server

			this.tryBackup();

		}

		this.home.mkdirs();

		this.internal = this.components.newComponent(this.spi, ContainerI.class).parent(ac.getContainer());//

		this.server = new Server(port);
		this.handlers = new HandlerList();

		this.server.setHandler(this.handlers);
		// not start here
		if (this.sslEnable) {

			ServerConnector ssl = this.getSslServerConnector(this.server);
			this.server.addConnector(ssl);

			LOG.info("sslEnabled,sslPort:" + this.sslPort + ",keyStore:" + this.keyStoreLocation + ",type:"
					+ this.keyStoreType);
		}

	}

	/* */
	@Override
	public WebAppI addWebApp(ActiveContext ac, String name, String cfgId) {
		if (this.isState(ServerI.STARTING, ServerI.RUNNING)) {

			throw new FsException("not supported while server is running");

		}

		JettyWebAppImpl wai = new JettyWebAppImpl(this);
		ac.activitor().context(ac).container(this.internal).object(wai).name(name).cfgId(cfgId).active();
		WebAppContext wac = wai.getJettyWebApp();
		// wac.get
		// this.server.addHandler(wac);//jetty 6

		if (wai.isRootContextPath()) {
			if (this.root != null) {
				throw new FsException("duplicated root web app");
			}
			// add to the last when start.
			this.root = wai;
		} else {
			this.handlers.addHandler(wac);//
		}

		LOG.info("addWebApp,contextPath:" + wai.getContextPath() + ",spi:" + ac.getSpi());
		return wai;
	}

	private ServerConnector getSslServerConnector(Server svr) {
		SslContextFactory contextFactory = new SslContextFactory();
		contextFactory.setKeyStoreType(this.keyStoreType);
		contextFactory.setKeyStorePath(this.keyStoreLocation);
		contextFactory.setKeyStorePassword(this.keyStorePassword);

		SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(contextFactory,
				org.eclipse.jetty.http.HttpVersion.HTTP_1_1.toString());

		HttpConfiguration config = new HttpConfiguration();
		config.setSecureScheme("https");
		config.setSecurePort(this.sslPort);
		// config.setOutputBufferSize(32786);
		// config.setRequestHeaderSize(8192);
		// config.setResponseHeaderSize(8192);
		HttpConfiguration sslConfiguration = new HttpConfiguration(config);
		HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(sslConfiguration);

		ServerConnector rt = new ServerConnector(svr, sslConnectionFactory, httpConnectionFactory);
		rt.setPort(this.sslPort);
		return rt;
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
			throw new FsException("backup history failed:" + his.getAbsolutePath());
		}
	}

	public File getHome() {
		return this.home;
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
		try {
			this.server.stop();
		} catch (Exception e) {
			throw FsException.toRtE(e);
		}//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.webserver.api.WebServerI#getRootWebApp()
	 */
	@Override
	public WebAppI getRootWebApp() {
		// TODO Auto-generated method stub
		return this.root;
	}

}
