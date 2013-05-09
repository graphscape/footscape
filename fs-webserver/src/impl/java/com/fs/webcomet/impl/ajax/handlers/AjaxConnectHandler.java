/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax.handlers;

import java.util.Map;
import java.util.UUID;

import com.fs.webcomet.impl.ajax.AjaxComet;
import com.fs.webcomet.impl.ajax.AjaxCometManagerImpl;
import com.fs.webcomet.impl.ajax.AjaxMsg;
import com.fs.webcomet.impl.ajax.AjaxMsgContext;
import com.fs.webcomet.impl.ajax.AjaxMsgHandler;

/**
 * @author wu
 * 
 */
public class AjaxConnectHandler extends AjaxMsgHandler {

	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxConnectHandler(Map<String, AjaxComet> sessionMap, AjaxCometManagerImpl manager) {
		super(sessionMap, manager);
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
		this.sessionMap.put(sid, as);//
		this.manager.onConnect(as);
		// response
		AjaxMsg am2 = new AjaxMsg(AjaxMsg.CONNECT.getSubPath("success"));
		am2.setProperty(AjaxMsg.PK_SESSION_ID, sid);
		amc.arc.write(am2);

	}
}
