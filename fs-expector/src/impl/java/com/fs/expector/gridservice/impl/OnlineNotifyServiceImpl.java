/**
 *  Jan 6, 2013
 */
package com.fs.expector.gridservice.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.expector.gridservice.api.OnlineNotifyServiceI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;

/**
 * @author wuzhen
 * 
 */
public class OnlineNotifyServiceImpl extends ConfigurableSupport implements OnlineNotifyServiceI {

	private static final Logger LOG = LoggerFactory.getLogger(OnlineNotifyServiceImpl.class);

	protected GridFacadeI facade;

	protected SessionManagerI sessionManager;

	protected TerminalManagerI terminalManager;

	@Override
	public void tryNotifyAccount(String accId, String path) {
		MessageI msg = new MessageSupport(path);
		this.tryNotifyAccount(accId, msg);
	}
	@Override
	public void tryNotifyAccount(String accId, MessageI msg) {
		// SessionManagerI sm = this.facade.getSessionManager();

		List<SessionGd> sL = this.sessionManager.getEntityListByField(SessionGd.ACCID, accId);
		boolean atleastone = false;
		for (SessionGd s : sL) {
			TerminalGd t2 = this.terminalManager.getTerminalBySessionId(s.getId(), false);
			if (t2 == null) {// TODO
				throw new FsException("TODO,remove old session when binding new session.");
			}
			try {
				this.terminalManager.sendMessage(t2.getId(), msg);
				atleastone = true;
			} catch (Exception e) {
				LOG.warn("failed to notify the terminal " + t2.getId() + " for account:" + accId
						+ ",message:" + msg);
			}
		}

		if (!atleastone) {
			LOG.warn("not able to notify any terminal for account:" + accId + ",message:" + msg);
		}

	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.facade = this.top.find(GridFacadeI.class, true);
		this.sessionManager = this.facade.getSessionManager();
		this.terminalManager = this.facade.getEntityManager(TerminalManagerI.class);
	}
	/*
	 *Apr 14, 2013
	 */
	@Override
	public void tryNotifyExpMessageCreated(String accId2, String expId1, String expId2) {
		// 
		MessageI msg = new MessageSupport("/notify/exp-message-created");
		msg.setHeader("expId2", expId2);
		msg.setHeader("expId1", expId1);
		this.tryNotifyAccount(accId2, msg);
		
	}
}
