/**
 * Jun 24, 2012
 */
package com.fs.uicommons.impl.test.handler;

import java.util.UUID;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.engine.api.support.HandlerSupport;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.SessionManagerI;

/**
 * @author wu
 * 
 */
public class LoginHandler extends HandlerSupport {

	protected SessionManagerI smanager;


	/*
	 *Dec 23, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		// 
		super.active(ac);
		this.smanager = this.container.find(SessionManagerI.class,true);
		
	}
	/* */
	@Override
	public void handle(HandleContextI sc) {

		super.handle(sc);

	}

	@Handle("submit")
	public void handleSubmit(RequestI req, ResponseI res) {
		SessionGd s = new SessionGd();
		String accId = "acc-001";
		s.setProperty(SessionGd.ACCID, accId);//
		
		String sid = this.smanager.createSession(s);
		
		PropertiesI<Object> pts = req.getPayloads();
		res.setPayload("isAnonymous", false);
		res.setPayload("sessionId", sid);//
		res.setPayload("accountId", accId);// account?
	}

	@Handle("anonymous")
	// create anonymous account.
	public void handleAnonymous(HandleContextI hc, RequestI req, ResponseI res) {
		String id = UUID.randomUUID().toString();

		res.setPayload("accountId", "acc-001");
		res.setPayload("password", "acc-001");

	}
}
