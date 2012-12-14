/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.endpoint;

import com.fs.commons.api.ActiveContext;
import com.fs.expector.api.endpoint.EndpointI;
import com.fs.expector.api.endpoint.EndpointManagerI;
import com.fs.expector.impl.dg.support.DgMapManagerImplSupport;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.api.WsListenerI;
import com.fs.websocket.api.WsManagerI;

/**
 * @author wuzhen
 * 
 */
public class WebSocketEndpointManagerImpl extends
		DgMapManagerImplSupport<EndpointI, WebSocketEndpointImpl> implements
		EndpointManagerI {

	public WebSocketEndpointManagerImpl() {
		super(EndpointI.class, WebSocketEndpointImpl.class);
	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);

		WsFactoryI wsf = ac.getContainer().find(WsFactoryI.class);
		WsManagerI wsm = wsf.getManager("default", true);//
		wsm.addListener(new WsListenerI() {

			@Override
			public void onConnect(WebSocketI ws) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMessage(WebSocketI ws, String ms) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onException(WebSocketI ws, Throwable t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onClose(WebSocketI ws, int statusCode, String reason) {
				// TODO Auto-generated method stub

			}
		});
	}

}
