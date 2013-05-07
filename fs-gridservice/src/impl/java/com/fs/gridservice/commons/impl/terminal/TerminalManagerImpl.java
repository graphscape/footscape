/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.terminal;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.support.CollectionHandler;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.GridedObjectManagerI;
import com.fs.gridservice.commons.api.gobject.EndPointGoI;
import com.fs.gridservice.commons.api.support.EntityGdManagerSupport;
import com.fs.gridservice.commons.api.terminal.MessageSendingContext;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;

/**
 * @author wuzhen
 * 
 */
public class TerminalManagerImpl extends EntityGdManagerSupport<TerminalGd> implements TerminalManagerI {

	public static final String N_WEBSOCKET_GOMANAGER = "endPointGoManager";

	public CollectionHandler<MessageSendingContext> beforeMessageSendingHandlers = new CollectionHandler<MessageSendingContext>();

	public static final int S_CLOSING = 1;

	public static final int S_CLOSED = 2;

	protected int status = 0;

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
	
		String id = tg.getAddress();
		GridedObjectManagerI<EndPointGoI> gom = this.facade.getGridedObjectManager(N_WEBSOCKET_GOMANAGER);
		EndPointGoI wso = gom.getGridedObject(id, true);

		MessageSendingContext msc = new MessageSendingContext(msg, tg, this.facade);
		this.beforeMessageSendingHandlers.handle(msc);
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
	public TerminalGd createTerminal(EndPointGoI wso) {

		GridedObjectManagerI<EndPointGoI> gom = this.facade.getGridedObjectManager(N_WEBSOCKET_GOMANAGER);
		gom.addGridedObject(wso);
		TerminalGd rt = new TerminalGd();
		rt.setProperty(TerminalGd.PK_ADDRESS, wso.getId());
		this.addEntity(rt);

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.terminal.TerminalManagerI#bindingClient
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void bindingClient(String tid, String clientId) {
		TerminalGd t = this.getTerminal(tid, true);
		t.setProperty(TerminalGd.PK_CLIENTID, clientId);
		this.addEntity(t);
		return;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public TerminalGd getTerminalBySessionId(String sid, boolean force) {
		List<TerminalGd> rt = this.getEntityListByField(TerminalGd.PK_SESSIONID, sid);
		if (rt.isEmpty()) {
			if (force) {
				throw new FsException("no sid:" + sid);
			} else {
				return null;
			}
		} else if (rt.size() == 1) {
			return rt.get(0);
		} else {
			throw new FsException("to many terminal for session:" + sid + ",all terminals:" + rt);
		}
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void unBindingSession(String tid) {
		//
		this.bindingSession(tid, null);
	}

	/*
	 * Apr 4, 2013
	 */
	@Override
	public void addBeforeMessageSendingHandler(HandlerI<MessageSendingContext> handler) {
		this.beforeMessageSendingHandlers.addHandler(handler);
	}


}
