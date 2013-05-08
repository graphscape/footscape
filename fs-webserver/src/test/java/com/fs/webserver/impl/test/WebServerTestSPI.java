/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl.test;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.SPISupport;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;

/**
 * @author wu
 * 
 */
public class WebServerTestSPI extends SPISupport {

	private static Logger LOG = Log.getLog();//

	/** */
	public WebServerTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {

		WebServerI ws = ac.getContainer().find(WebServerI.class);

		// WebAppI wa = ws.getWebApp("ROOT");
		WebAppI wa = ws.addWebApp(ac, "TestAPP", this.id + ".WebApp.TESTAPP");

		wa.addServlet(ac, "TEST_HOLDER", this.getId() + ".servletHolder.TEST_HOLDER");// app
		//
		wa.addResource(ac, "testres", this.getId() + ".webResource.test");
		//
		try {
			wa.addResource(ac, "testResourceNotFound", this.id + ".webResource.testResourceNotFound");
		} catch (FsException e) {
			if (e.getMessage().contains("notfound.jar")) {
				LOG.info("just testing,expected exception:" + e.getMessage());

			} else {
				throw e;
			}
		}
		
	}

	/*
	 *Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		// 
		
	}

}
