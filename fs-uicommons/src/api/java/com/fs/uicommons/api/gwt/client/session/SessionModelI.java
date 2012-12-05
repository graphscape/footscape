package com.fs.uicommons.api.gwt.client.session;

import com.fs.uicore.api.gwt.client.LazyI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

public interface SessionModelI extends ModelI{
	
	public static final Location L_ACCOUNT = Location.valueOf("_account");

	public static final Location L_XMPP_USER = Location.valueOf("_xmppUser");

	public static final Location L_XMPP_PASSWORD = Location.valueOf("_xmppPassword");

	public static final Location L_DOMAIN = Location.valueOf("_domain");

	public static final Location L_LOGIN_REQUIRED = Location
			.valueOf("_loginRequired");

	public static final Location L_IS_AUTHED = Location.valueOf("_isAuthed");
	
	public static final Location L_IS_ANONYMOUS = Location.valueOf("_isAnonymous");

	public void setLoginId(String sid);

	public String getSessionId();

	public void setAccount(String acc);

	public String getAccount();
	
	public String getDomain();
	
	public String getMucDomain();
	
	public String getXmppUser();
	
	public String getXmppPassword();

	public boolean isLoginRequired();
	
	public boolean isAuthed();
	
	public void setAuthed(boolean authed);

	public void setLoginRequired(boolean b);

	public void setXmppUser(String xuser);

	public void setXmppPassword(String xpass);
	
	public void addAuthedHandler(HandlerI<ModelValueEvent> eh);
	
	public <T> void addAuthedProcessor(LazyI<T> lazy);
	
	public boolean isAnonymous();
	
	public void setIsAnonymous(boolean an);
	

}
