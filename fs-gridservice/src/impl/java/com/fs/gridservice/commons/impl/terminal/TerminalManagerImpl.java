/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.terminal;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.GridedObjectManagerI;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.support.EntityGdManagerSupport;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;

/**
 * @author wuzhen
 * 
 */
public class TerminalManagerImpl extends EntityGdManagerSupport<TerminalGd>
		implements TerminalManagerI {

	public static final String N_WEBSOCKET_GOMANAGER = "webSocketGoManager";

	/**
	 * @param name
	 * @param wcls
	 */
	public TerminalManagerImpl() {
		super("terminal", TerminalGd.class);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		// TODO Auto-generated method stub
		super.active(ac);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.terminal.TerminalManagerI#bindingWebSocket
	 * (java.lang.String, java.lang.String)
	 */

	@Override
	public void sendMessage(MessageI msg) {
		String termId = msg.getHeader("terminalId", true);
		this.sendMessage(termId, msg);
	}

	@Override
	public void sendMessage(String termId, MessageI msg) {
		TerminalGd tg = this.getEntity(termId);
		if (tg == null) {
			throw new FsException("TODO");
		}
		String pro = tg.getProtocol();
		if (!"websocket".equals(pro)) {
			throw new FsException("protocol:" + pro
					+ " not supported for send message to");
		}

		String wsoId = tg.getAddress();
		GridedObjectManagerI<WebSocketGoI> gom = this.facade
				.getGridedObjectManager(N_WEBSOCKET_GOMANAGER);
		WebSocketGoI wso = gom.getGridedObject(wsoId, true);
		wso.sendMessage(msg);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.terminal.TerminalManagerI#getTerminal(
	 * java.lang.String)
	 */
	@Override
	public TerminalGd getTerminal(String tid) {
		// TODO Auto-generated method stub
		return this.getEntity(tid);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.terminal.TerminalManagerI#sendTextMessage
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void sendTextMessage(String tId, String text) {
		MessageI msg = new MessageSupport();
		msg.setPayload("text", text);
		this.sendMessage(tId, msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.terminal.TerminalManagerI#getTerminal(
	 * java.lang.String, boolean)
	 */
	@Override
	public TerminalGd getTerminal(String id, boolean force) {

		return this.getEntity(id, force);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.terminal.TerminalManagerI#bindingSession
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void bindingSession(String tid, String sid) {
		TerminalGd t = this.getTerminal(tid, true);
		t.setProperty(TerminalGd.PK_SESSIONID, sid);
		this.addEntity(t);
		return;
	}

	@Override
	public TerminalGd webSocketTerminal(WebSocketGoI wso) {

		GridedObjectManagerI<WebSocketGoI> gom = this.facade
				.getGridedObjectManager(N_WEBSOCKET_GOMANAGER);
		gom.addGridedObject(wso);
		TerminalGd rt = new TerminalGd();
		rt.setProperty(TerminalGd.PK_PROTOCAL, "websocket");
		rt.setProperty(TerminalGd.PK_ADDRESS, wso.getId());
		this.addEntity(rt);

		return rt;
	}

	@Override
	public TerminalGd web20Terminal(String address, PropertiesI<Object> pts) {
		TerminalGd rt = new TerminalGd();
		rt.setProperties(pts);
		rt.setProperty(TerminalGd.PK_PROTOCAL, "web20");
		rt.setProperty(TerminalGd.PK_ADDRESS, address);
		this.addEntity(rt);
		return rt;
	}

	/* (non-Javadoc)
	 * @see com.fs.gridservice.commons.api.terminal.TerminalManagerI#bindingClient(java.lang.String, java.lang.String)
	 */
	@Override
	public void bindingClient(String tid, String clientId) {
		TerminalGd t = this.getTerminal(tid, true);
		t.setProperty(TerminalGd.PK_CLIENTID, clientId);
		this.addEntity(t);
		return;
	}

}
