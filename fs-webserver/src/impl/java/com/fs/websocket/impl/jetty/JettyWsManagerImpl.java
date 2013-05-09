/**
 *  Dec 11, 2012
 */
package com.fs.websocket.impl.jetty;

import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.fs.webcomet.api.CometCreatingInterceptorI;
import com.fs.webcomet.api.CometProtocolI;
import com.fs.webcomet.api.support.CometManagerSupport;

/**
 * @author wuzhen
 *         <p>
 *         1-1 mapping to Servlet
 */
public class JettyWsManagerImpl extends CometManagerSupport implements WebSocketCreator {

	protected WebSocketServletFactory factory;

	public JettyWsManagerImpl(CometProtocolI cp,String name) {
		super(cp,name);
	}

	@Override
	public Object createWebSocket(UpgradeRequest arg0, UpgradeResponse arg1) {

		String id = this.nextId();

		JettyCometImpl rt = new JettyCometImpl(id, arg0);

		rt.addListener(this);//
		
		for (CometCreatingInterceptorI ci : this.interceptors) {
			ci.afterWsCreated(rt);
		}

		return rt;

	}

	/**
	 * Dec 11, 2012
	 */
	public void configure(WebSocketServletFactory factory) {
		//
		this.factory = factory;
		this.factory.setCreator(this);

	}

}
