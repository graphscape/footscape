/**
 *  Dec 11, 2012
 */
package com.fs.webcomet.impl.ajax;

import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.fs.webcomet.api.CometProtocolI;
import com.fs.webcomet.api.support.CometManagerSupport;

/**
 * @author wuzhen
 *         <p>
 *         1-1 mapping to Servlet
 */
public class AjaxCometManagerImpl extends CometManagerSupport {

	protected WebSocketServletFactory factory;

	public AjaxCometManagerImpl(CometProtocolI cp,String name) {
		super(cp,name);
	}
	
}
