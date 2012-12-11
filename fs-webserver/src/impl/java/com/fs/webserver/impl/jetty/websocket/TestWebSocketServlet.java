/**
 *  Dec 11, 2012
 */
package com.fs.webserver.impl.jetty.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


/**
 * @author wuzhen
 * 
 */
public class TestWebSocketServlet extends WebSocketServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jetty.websocket.servlet.WebSocketServlet#configure(org.eclipse
	 * .jetty.websocket.servlet.WebSocketServletFactory)
	 */
	@Override
	public void configure(WebSocketServletFactory factory) {
		// TODO Auto-generated method stub
		//factory.register(EchoWebsocket.class);
	}
}