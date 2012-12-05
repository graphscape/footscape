/**
 * Jul 23, 2012
 */
package com.fs.uiserver.impl.handler.client;

import java.util.UUID;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.expapp.wrapper.Session;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.annotation.Handle;
import com.fs.uiserver.impl.filter.SessionFilter;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu
 * @see SessionFilter
 */
public class ClientHandler extends UiHandlerSupport {

	protected PropertiesI<String> clientParameters;

	@Handle("init")
	public void handleInit(HandleContextI hc) {
		// create session for client init.
		// PropertiesI<Object> session = new MapProperties<Object>();

		String sid = UUID.randomUUID().toString();
		Session s = new Session().forCreate(this.dataService);
		String locale = this.resolveLocale(hc);
		s.setLocale(locale);
		s.save(true);

		sid = s.getId();//

		hc.getResponse().setPayload("sessionId", sid);// TODO

		hc.getResponse().setPayload("parameters", this.clientParameters);

	}

	protected String resolveLocale(HandleContextI hc) {
		String rt = (String) hc.getRequest().getPayload("preferedLocale");
		if (rt != null) {
			return rt;// is provided by client side,may saved in client side
		}
		// http request header:
		// TODO support other protocol other than http.
		String al = rt = hc.getRequest().getHeader("Accept-Language");

		// http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4

		if (al == null) {// not http request or some other cause
			return "en";// default
		}
		rt = new AcceptLanguage(al).getLocale();

		return rt;
	}

	/*
	 * Nov 18, 2012
	 */
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

}
