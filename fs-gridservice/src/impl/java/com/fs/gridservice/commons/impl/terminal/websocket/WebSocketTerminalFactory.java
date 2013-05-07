/**
 *  
 */
package com.fs.gridservice.commons.impl.terminal.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.gobject.EndPointGoI;
import com.fs.gridservice.commons.impl.gobject.WebSoketEndPointGoImpl;
import com.fs.gridservice.commons.impl.terminal.TerminalFactory;
import com.fs.websocket.api.WebSocketI;

/**
 * @author wu
 * 
 */
public class WebSocketTerminalFactory extends TerminalFactory<WebSocketI> {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketTerminalFactory.class);

	protected static String PK_WSGO = "_webSocketGo";

	/**
	 * @param gf
	 */
	public WebSocketTerminalFactory(ContainerI c, GridFacadeI gf) {
		super(c, gf);
	}

	/**
	 * @param wso
	 */
	public void onConnect(WebSocketI ws) {
		// TODO Auto-generated method stub
		EndPointGoI wso = new WebSoketEndPointGoImpl(ws, this.messageCodec);
		setWso(ws, wso);

		if (LOG.isDebugEnabled()) {
			LOG.debug("onConnected,wsoId:" + wso.getId() + ",wsId:" + ws.getId());
		}

	}

	public static void setWso(WebSocketI ws, EndPointGoI wso) {
		ws.setProperty(PK_WSGO, wso);
	}

	public EndPointGoI getEndPointGo(WebSocketI ws) {

		return (EndPointGoI) ws.getProperty(PK_WSGO);
	}

}
