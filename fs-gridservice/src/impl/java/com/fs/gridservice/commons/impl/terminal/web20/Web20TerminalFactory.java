/**
 *  
 */
package com.fs.gridservice.commons.impl.terminal.web20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.impl.terminal.TerminalFactory;
import com.fs.websocket.api.WebSocketI;

/**
 * @author wu
 * 
 */
public class Web20TerminalFactory extends TerminalFactory<Web20Session> {

	private static final Logger LOG = LoggerFactory.getLogger(Web20TerminalFactory.class);

	protected static String PK_WSGO = "_webSocketGo";

	/**
	 * @param gf
	 */
	public Web20TerminalFactory(ContainerI c, GridFacadeI gf) {
		super(c, gf);
	}

	/**
	 * @param wso
	 */
	public void onConnect(Web20Session ws) {

	}

	public void onClose(Web20Session ws) {

		// super.onClose(tid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.commons.impl.terminal.TerminalFactory#create()
	 */
	@Override
	public TerminalGd create(Web20Session ws) {

		return null;
	}

	public static void setWso(WebSocketI ws, WebSocketGoI wso) {
		ws.setProperty(PK_WSGO, wso);
	}

	public static WebSocketGoI getWso(WebSocketI ws) {
		return (WebSocketGoI) ws.getProperty(PK_WSGO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.impl.terminal.TerminalFactory#sendReady(java
	 * .lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendReady(Web20Session ws, String id, String tid, String cid) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.impl.terminal.TerminalFactory#getTerminalId
	 * (java.lang.Object)
	 */
	@Override
	public String getTerminalId(Web20Session t) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.impl.terminal.TerminalFactory#getClientId(
	 * java.lang.Object)
	 */
	@Override
	public String getClientId(Web20Session t) {
		// TODO Auto-generated method stub
		return null;
	}

}
