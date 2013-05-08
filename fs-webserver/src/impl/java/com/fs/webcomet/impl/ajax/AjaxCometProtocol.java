/**
 *  
 */
package com.fs.webcomet.impl.ajax;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.webcomet.api.CometManagerI;
import com.fs.webcomet.api.support.CometProtocolSupport;
import com.fs.webserver.api.ServletHolderI;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;
import com.fs.websocket.impl.jetty.JettyWsManagerImpl;

/**
 * @author wu
 * 
 */
public class AjaxCometProtocol extends CometProtocolSupport {

	/**
	 * @param name
	 * @param cfg
	 */
	public AjaxCometProtocol(String name, Configuration cfg) {
		super(name, cfg);
	}

	@Override
	public CometManagerI createManager(ActiveContext ac, String name) {
		WebServerI wbs = ac.getContainer().find(WebServerI.class);
		WebAppI wap = wbs.getWebApp("aja");
		// NOTE configuration is load and changed here.
		Configuration cfg = Configuration.properties(this.configId+".servletHolderTemplate");// a new
																	// configuration
		String path = cfg.getProperty("path", true);
		path = path.replace("{name}", name);// NOTE
		cfg.setProperty("path", path);//

		ServletHolderI sh = wap.addServlet(ac, name, cfg);

		AjaxCometServlet sev = (AjaxCometServlet) sh.getServlet();//
		AjaxCometManagerImpl rt = sev.attachManager(this, name);
		return rt;
	}

}
