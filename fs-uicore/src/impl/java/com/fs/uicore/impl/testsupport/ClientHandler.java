/**
 * Jun 24, 2012
 */
package com.fs.uicore.impl.testsupport;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.MapProperties;
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
public class ClientHandler extends HandlerSupport {

	protected PropertiesI<String> clientParameters;

	/* */
	@Override
	public void handle(HandleContextI sc) {

		super.handle(sc);

	}

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		Configuration cfg = this.config.getPropertyAsConfiguration(
				"client.parameters", true);
		this.clientParameters = MapProperties.valueOf(cfg.getAsMap());// encode
																		// as
																		// PropertiesI
	}

	@Handle("init")
	public void handleInit(HandleContextI hc, RequestI req, ResponseI res) {

		String sid = "test-session-001";

		hc.getResponse().setPayload("sessionId", sid);// TODO

		hc.getResponse().setPayload("parameters", this.clientParameters);
	}

}
