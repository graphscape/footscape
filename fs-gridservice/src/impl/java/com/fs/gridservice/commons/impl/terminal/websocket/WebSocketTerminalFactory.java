/**
 *  
 */
package com.fs.gridservice.commons.impl.terminal.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.impl.gobject.WebSoketGoImpl;
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
		WebSocketGoI wso = new WebSoketGoImpl(ws, this.messageCodec);
		setWso(ws, wso);

		if (LOG.isDebugEnabled()) {
			LOG.debug("onConnected,wsoId:" + wso.getId() + ",wsId:" + ws.getId());
		}

	}

	public void onClose(WebSocketI ws) {
		WebSocketGoI wso = this.getWso(ws);

		if (LOG.isDebugEnabled()) {
			LOG.debug("onClose,wsoId:" + wso.getId() + ",wsId:" + ws.getId());
		}

		// server is ready means the terminal object is created and bind to this
		// WS.
		// and also the Client object is created and bind to this terminal.
		// TODO move to a common place for exit logic

		String tid = wso.getTerminalId(false);
		if (tid == null) {//
			LOG.warn("ws closed,but no terminal is bound with.");
			return;
		}
		super.onClose(tid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.commons.impl.terminal.TerminalFactory#create()
	 */
	@Override
	public TerminalGd create(WebSocketI ws) {
		TerminalManagerI tm = this.facade.getEntityManager(TerminalManagerI.class);
		WebSocketGoI wso = this.getWso(ws);
		TerminalGd t = tm.webSocketTerminal(wso);
		return t;
	}

	public static void setWso(WebSocketI ws, WebSocketGoI wso) {
		ws.setProperty(PK_WSGO, wso);
	}

	public static WebSocketGoI getWso(WebSocketI ws) {
		return (WebSocketGoI) ws.getProperty(PK_WSGO);
	}

	@Override
	public void sendReady(WebSocketI ws, String id, String tid, String cid) {
		WebSocketGoI wso = getWso(ws);
		wso.sendReady(id, tid, cid);//

	}

	@Override
	public String getTerminalId(WebSocketI t) {
		return getWso(t).getTerminalId(true);
	}

	@Override
	public String getClientId(WebSocketI t) {
		return getWso(t).getClientId(true);

	}

}
