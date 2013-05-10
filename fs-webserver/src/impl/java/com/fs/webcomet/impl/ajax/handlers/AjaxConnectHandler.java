/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax.handlers;

import java.util.UUID;

import com.fs.commons.api.session.SessionI;
import com.fs.commons.api.session.SessionManagerI;
import com.fs.webcomet.impl.ajax.AjaxComet;
import com.fs.webcomet.impl.ajax.AjaxCometManagerImpl;
import com.fs.webcomet.impl.ajax.AjaxCometServlet;
import com.fs.webcomet.impl.ajax.AjaxMsg;
import com.fs.webcomet.impl.ajax.AjaxMsgContext;
import com.fs.webcomet.impl.ajax.AjaxMsgHandler;

/**
 * @author wu
 * 
 */
public class AjaxConnectHandler extends AjaxMsgHandler {

	private long timeout = 120 * 1000;// TODO

	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxConnectHandler(SessionManagerI sessionMap, AjaxCometManagerImpl manager) {
		super(false, sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		//
		// do connection
		String sid = UUID.randomUUID().toString();
		
		AjaxComet as = new AjaxComet(sid);
		SessionI s = this.sessionMap.createSession(sid, timeout);//
		s.setProperty(AjaxCometServlet.SK_COMET, as);

		this.manager.onConnect(as);
		// response
		AjaxMsg am2 = new AjaxMsg(AjaxMsg.CONNECT.getSubPath("success"));
		am2.setProperty(AjaxMsg.PK_CONNECT_SESSION_ID, sid);
		amc.arc.write(am2);

	}
}
