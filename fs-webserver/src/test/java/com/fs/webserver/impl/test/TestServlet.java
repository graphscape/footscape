/**
 * Jul 10, 2012
 */
package com.fs.webserver.impl.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wu
 *
 */
public class TestServlet extends HttpServlet{

	public static String RESPONSE = "this is response from test servelt";
	/* */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write(RESPONSE);
		
	}

}
