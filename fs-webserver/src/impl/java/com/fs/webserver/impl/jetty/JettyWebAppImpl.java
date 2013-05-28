/**
 * Jul 12, 2012
 */
package com.fs.webserver.impl.jetty;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ActivitorI;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.webserver.api.ServletHolderI;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebResourceI;

/**
 * @author wu
 * 
 */
public class JettyWebAppImpl extends ConfigurableSupport implements WebAppI {

	private static final Logger LOG = LoggerFactory.getLogger(JettyWebAppImpl.class);

	private ServletContainer internal;

	private WebResourceContainer internal2;//

	private WebAppContext jettyWebApp;

	private JettyWebServerImpl jettyWebServer;

	private File home;

	private Map<String, ServletHolderI> pathMap;

	/** */
	public JettyWebAppImpl(JettyWebServerImpl jettyWebServerImpl) {
		this.jettyWebServer = jettyWebServerImpl;
		this.pathMap = new HashMap<String, ServletHolderI>();
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);

		this.internal = new ServletContainer(this.components.newComponent(this.spi, ContainerI.class).parent(
				ac.getContainer()), this);// NOTE
		this.internal2 = new WebResourceContainer(this.components.newComponent(this.spi, ContainerI.class)
				.parent(ac.getContainer()), this);// NOTE

		this.jettyWebApp = new WebAppContext();
		String cpath = this.config.getProperty("context.path", true);
		String wpath = cpath;
		if (cpath.length() == 0) {// is root
			wpath = "root";
		}
		String war = this.jettyWebServer.getHome().getAbsolutePath() + File.separator + wpath;
		this.home = new File(war);

		if (!this.home.exists()) {
			this.home.mkdirs();// NOTE
			// this.home.deleteOnExit();
		}
		this.jettyWebApp.setWar(war);//
		this.jettyWebApp.setContextPath(cpath);
	}

	@Override
	protected void doAttach() {

	}

	public String getContextPath() {
		return this.jettyWebApp.getContextPath();//
	}

	public File getHome() {
		return this.home;
	}

	@Override
	public ServletHolderI addServlet(ActiveContext ac, String name, Configuration cfg) {
		return this.addServlet(ac, name, cfg, null);
	}

	/* */
	@Override
	public ServletHolderI addServlet(ActiveContext ac, String name, String cfgId) {
		return this.addServlet(ac, name, null, cfgId);
	}

	protected ServletHolderI addServlet(ActiveContext ac, String name, Configuration cfg, String cfgId) {
		JettyWsServletHolder jsh = new JettyWsServletHolder();

		ActivitorI act = ac.activitor().object(jsh).container(this.internal).name(name);
		if (cfgId != null) {
			act.cfgId(cfgId);
		}
		if (cfg != null) {
			act.configuration(cfg);
		}
		act.active();
		String path = jsh.getPath();
		if (null != this.pathMap.get(path)) {
			throw new FsException("path duplicated:" + path);
		}

		this.jettyWebApp.addServlet(jsh.jettyHolder, path);

		LOG.info("addServlet,webApp:" + this.getContextPath() + ",name:" + name + ",path:" + jsh.getPath()
				+ ",cfgId:" + cfgId + ",spi:" + ac.getSpi());//
		return jsh;
	}

	/* */
	@Override
	public WebResourceI addResource(ActiveContext ac, String name, String cfgId) {
		WebResourceI jsh = new WebResourceImpl(this);
		ac.activitor().object(jsh).container(this.internal).cfgId(cfgId).name(name).active();
		LOG.info("addResource,name" + name + ",cfgId:" + cfgId + ",spi:" + ac.getSpi());//
		return jsh;
	}

	public boolean isRootContextPath() {
		return this.getContextPath().length() == 0;
	}

	/**
	 * @return the jettyWebApp
	 */
	public WebAppContext getJettyWebApp() {
		return jettyWebApp;
	}

	/* */
	@Override
	public ServletHolderI getServlet(String name) {// TODO export internal.

		return this.internal.finder(ServletHolderI.class).name(name).find(true);

	}

	/* */
	@Override
	public WebResourceI getResource(String name) {

		return this.internal2.finder(WebResourceI.class).name(name).find(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.webserver.api.WebAppI#setWellcomeFiles(java.lang.String[])
	 */
	@Override
	public void setWellcomeFiles(String[] wfs) {
		this.jettyWebApp.setWelcomeFiles(wfs);
	}

}
