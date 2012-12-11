/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webserver.impl.test.cases;

import junit.framework.TestCase;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author wu
 * 
 */
public class TestJetty9 extends TestCase {

	public void test() throws Exception {
		String jetty_home = "/home/wu/.fs/webserver";// System.getProperty("jetty.home",
														// "..");

		Server server = new Server(8080);

		server.start();
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(jetty_home + "/ROOT");
		server.setHandler(webapp);
		//server.addBean(webapp);//
		// server.join();
		webapp.stop();
		webapp.destroy();
		server.stop();
		server.destroy();//
	}

}
