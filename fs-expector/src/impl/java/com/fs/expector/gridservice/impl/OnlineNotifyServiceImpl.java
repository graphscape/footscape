/**
 *  Jan 6, 2013
 */
package com.fs.expector.gridservice.impl;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
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

	protected GridFacadeI facade;

	protected SessionManagerI sessionManager;

	protected TerminalManagerI terminalManager;

	@Override
	public void tryNotifyAccount(String accId, MessageI msg) {
		//SessionManagerI sm = this.facade.getSessionManager();

		List<SessionGd> sL = this.sessionManager.getEntityListByField(SessionGd.ACCID, accId);

		for (SessionGd s : sL) {
			TerminalGd t2 = this.terminalManager.getTerminalBySessionId(s.getId(), false);
			if (t2 == null) {// TODO
				throw new FsException("TODO,remove old session when binding new session.");
			}

			this.terminalManager.sendMessage(t2.getId(), msg);

		}
	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.facade = this.top.find(GridFacadeI.class, true);
		this.sessionManager = this.facade.getSessionManager();
		this.terminalManager = this.facade.getEntityManager(TerminalManagerI.class);
	}
}
