/**
 *  Dec 11, 2012
 */
package com.fs.webserver.impl.jetty.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */

public class JettyWsServletImpl extends WebSocketServlet {

	protected JettyWsManagerImpl manager;
	
	
	public JettyWsServletImpl(){
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jetty.websocket.servlet.WebSocketServlet#configure(org.eclipse
	 * .jetty.websocket.servlet.WebSocketServletFactory)
	 */
	@Override
	public void configure(WebSocketServletFactory factory) {
		
		if(this.manager == null){
			throw new FsException("manager should be set before servlet init");
		}
		manager.configure(factory);	
	}

	/**
	 *Dec 11, 2012
	 */
	public JettyWsManagerImpl attachManager(String name) {
		this.manager =  new JettyWsManagerImpl(name);
		return this.manager;
	}
}