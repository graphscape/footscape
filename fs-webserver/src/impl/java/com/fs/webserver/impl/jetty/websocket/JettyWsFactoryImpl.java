/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webserver.impl.jetty.websocket;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.webserver.api.ServletHolderI;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;
import com.fs.webserver.api.websocket.WsFactoryI;
import com.fs.webserver.api.websocket.WSManagerI;

/**
 * @author wu
 * 
 */
public class JettyWsFactoryImpl extends ConfigurableSupport implements
		WsFactoryI {
	protected Map<String, JettyWsManagerImpl> managers;

	public JettyWsFactoryImpl() {

		this.managers = new HashMap<String, JettyWsManagerImpl>();

	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public WSManagerI addManager(ActiveContext ac, String name) {
		// create a
		WebServerI wbs = this.container.find(WebServerI.class);
		WebAppI wap = wbs.getWebApp("wsa");
		// NOTE configuration is load and changed here.
		Configuration cfg = Configuration.properties(this.configId + "."
				+ "servletHolder.WS_MGR");
		String path = cfg.getProperty("path", true);
		path = path.replace("{name}", name);//NOTE 
		cfg.setProperty("path", path);//

		ServletHolderI sh = wap.addServlet(ac, name, cfg);
		JettyWsServletImpl sev = (JettyWsServletImpl) sh.getServlet();//
		JettyWsManagerImpl rt = sev.attachManager(name);
		this.managers.put(name, rt);

		return rt;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public WSManagerI getManager(String name, boolean force) {
		//
		WSManagerI rt = this.managers.get(name);
		if (rt == null && force) {
			throw new FsException("no manager found:" + name);
		}
		return rt;
	}

}
