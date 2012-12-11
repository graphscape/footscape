/**
 * Jul 12, 2012
 */
package com.fs.webserver.impl.jetty;

import java.io.File;

import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.webserver.api.ServletHolderI;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebResourceI;

/**
 * @author wu
 * 
 */
public class JettWebAppImpl extends ConfigurableSupport implements WebAppI {

	private static final Logger LOG = LoggerFactory
			.getLogger(JettWebAppImpl.class);

	private ServletContainer internal;

	private WebResourceContainer internal2;//

	private WebAppContext jettyWebApp;

	private JettyWebServerImpl jettyWebServer;

	private File home;

	/** */
	public JettWebAppImpl(JettyWebServerImpl jettyWebServerImpl) {
		this.jettyWebServer = jettyWebServerImpl;
	}

	/* */
	@Override
	public void active(ActiveContext ac) {

		super.active(ac);
		this.internal = new ServletContainer(ac.getContainer()
				.find(ContainerI.FactoryI.class).newContainer(), this);// NOTE
		this.internal2 = new WebResourceContainer(ac.getContainer()
				.find(ContainerI.FactoryI.class).newContainer(), this);// NOTE

		this.jettyWebApp = new WebAppContext();
		String cpath = this.config.getProperty("context.path", true);
		
		String war = this.jettyWebServer.getHome().getAbsolutePath()
				+ File.separator + cpath;
		this.home = new File(war);

		if (!this.home.exists()) {
			this.home.mkdirs();// NOTE
			//this.home.deleteOnExit();
		}
		this.jettyWebApp.setWar(war);//
		this.jettyWebApp.setContextPath(cpath);
	}
	@Override
	protected void doAttach(){
		
	}

	public String getContextPath() {
		return this.jettyWebApp.getContextPath();//
	}

	public File getHome() {
		return this.home;
	}

	/* */
	@Override
	public ServletHolderI addServlet(ActiveContext ac, String name, String cfgId) {
		JettyServletHolder jsh = new JettyServletHolder();
		ac.activitor().object(jsh).container(this.internal).cfgId(cfgId)
				.name(name).active();

		this.jettyWebApp.addServlet(jsh.jettyHolder, jsh.getPath());
				
		LOG.info("addServlet,webApp:" + this.getContextPath() + ",name:" + name
				+ ",path:" + jsh.getPath() + ",cfgId:" + cfgId + ",spi:"
				+ ac.getSpi());//
		return jsh;
	}

	/* */
	@Override
	public WebResourceI addResource(ActiveContext ac, String name, String cfgId) {
		WebResourceI jsh = new WebResourceImpl(this);
		ac.activitor().object(jsh).container(this.internal).cfgId(cfgId)
				.name(name).active();
		LOG.info("addResource,name" + name + ",cfgId:" + cfgId + ",spi:"
				+ ac.getSpi());//
		return jsh;
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

}
