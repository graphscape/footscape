/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.terminal;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.gridservice.commons.api.GridedObjectManagerI;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.impl.support.EntityGdManagerSupport;

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
			throw new FsException("TODO");
		}

		String wsoId = tg.getAddress();
		GridedObjectManagerI<WebSocketGoI> gom = this.facade
				.getGridedObjectManager(N_WEBSOCKET_GOMANAGER);
		WebSocketGoI wso = gom.getGridedObject(wsoId);
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

}
