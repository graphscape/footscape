/**
 * Jun 24, 2012
 */
package com.fs.uicommons.impl.test.handler;

import java.util.UUID;

import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.engine.api.support.HandlerSupport;

/**
 * @author wu
 * 
 */
public class LoginHandler extends HandlerSupport {

	/* */
	@Override
	public void handle(HandleContextI sc) {

		super.handle(sc);

	}

	@Handle("submit")
	public void handleSubmit(RequestI req, ResponseI res) {
		PropertiesI<Object> pts = req.getPayloads();
		res.setPayload("isAnonymous", false);
		res.setPayload("loginId", "login-001");//
		res.setPayload("accountId", "acc-001");// account?
	}

	@Handle("anonymous")
	// create anonymous account.
	public void handleAnonymous(HandleContextI hc, RequestI req, ResponseI res) {
		String id = UUID.randomUUID().toString();

		res.setPayload("accountId", "acc-001");
		res.setPayload("password", "acc-001");

	}
}
