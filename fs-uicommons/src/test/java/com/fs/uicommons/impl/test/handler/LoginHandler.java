/**
 * Jun 24, 2012
 */
package com.fs.uicommons.impl.test.handler;

import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
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

	public void handleSubmit(RequestI req, ResponseI res) {
		PropertiesI<Object> pts = req.getPayloads();

		String username = (String) pts.getProperty("username");
		String password = (String) pts.getProperty("password");
		//
		res.getPayloads().setProperty("sessionId", "session-001");//
		res.getPayloads().setProperty("account", "account-001");
		res.getPayloads().setProperty("domain", "domain-001");
	
	}
	

}
