/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl.test;

import org.mortbay.log.Log;

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

	/** */
	public WebServerTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		WebServerI ws = ac.getContainer().find(WebServerI.class);

		// WebAppI wa = ws.getWebApp("ROOT");
		WebAppI wa = ws.addWebApp(ac, "TestAPP", this.id + ".WebApp.TESTAPP");

		wa.addServlet(ac, "TEST_HOLDER", this.getId()
				+ ".servletHolder.TEST_HOLDER");// app
		//
		wa.addResource(ac, "testres", this.getId() + ".webResource.test");
		//
		try {
			wa.addResource(ac, "testResourceNotFound", this.id
					+ ".webResource.testResourceNotFound");
		} catch (FsException e) {
			if (e.getMessage().contains("notfound.jar")) {
				Log.info("expected exception?", e);

			} else {
				throw e;
			}
		}
	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
