package com.fs.uicommons.impl.gwt.client.session;

import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.LazyI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

public class SessionModelImpl extends ModelSupport implements SessionModelI {

	public SessionModelImpl(String name) {
		super(name);

	}

	@Override
	public void setLoginId(String sid) {
		this.setDefaultValue(sid);
	}

	@Override
	public String getSessionId() {
		// TODO Auto-generated method stub
		return (String) this.getDefaultValue();
	}

	@Override
	public void setAccount(String acc) {
		this.setValue(L_ACCOUNT, acc);
	}

	@Override
	public String getAccount() {
		return (String) this.getValue(L_ACCOUNT);

	}

	@Override
	public boolean isAuthed() {
		return this.getValue(Boolean.class, L_IS_AUTHED, Boolean.FALSE);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.auth.SessionModelI#setLoginRequired(boolean
	 * )
	 */
	@Override
	public void setLoginRequired(boolean b) {
		this.setValue(L_LOGIN_REQUIRED, b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.auth.SessionModelI#isLoginRequired()
	 */
	@Override
	public boolean isLoginRequired() {
		return this.getValue(Boolean.class, L_LOGIN_REQUIRED, Boolean.FALSE);

	}

	@Override
	public void setAuthed(boolean authed) {
		this.setValue(L_IS_AUTHED, authed);
	}

	/*
	 * Oct 23, 2012
	 */
	@Override
	public String getDomain() {
		//
		return this.getValue(String.class, SessionModelI.L_DOMAIN);

	}

	/*
	 * Oct 23, 2012
	 */
	@Override
	public String getMucDomain() {
		//
		return "muc." + this.getDomain();
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public String getXmppUser() {
		//
		return this.getValue(String.class, L_XMPP_USER);
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public String getXmppPassword() {
		//
		return this.getValue(String.class, L_XMPP_PASSWORD);

	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public void setXmppUser(String xuser) {
		this.setValue(L_XMPP_USER, xuser);
	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public void setXmppPassword(String xpass) {
		this.setValue(L_XMPP_PASSWORD, xpass);
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public void addAuthedHandler(HandlerI<ModelValueEvent> eh) {
		this.addValueHandler(L_IS_AUTHED, Boolean.TRUE, eh);
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public <T> void addAuthedProcessor(final LazyI<T> lazy) {
		this.addValueProcessor(L_IS_AUTHED, Boolean.TRUE, lazy);
	}

	/*
	 * Nov 25, 2012
	 */
	@Override
	public boolean isAnonymous() {
		//
		return this.getValue(Boolean.class, L_IS_ANONYMOUS, Boolean.FALSE);

	}

	/*
	 *Nov 25, 2012
	 */
	@Override
	public void setIsAnonymous(boolean an) {
		this.setValue(L_IS_ANONYMOUS, an);
	}

}
