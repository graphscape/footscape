package com.fs.uicommons.impl.gwt.client.session;

import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicommons.api.gwt.client.session.event.AccountUpdateEvent;
import com.fs.uicore.api.gwt.client.LazyI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

public class SessionModelImpl extends ModelSupport implements SessionModelI {

	private String accountId;

	private boolean isAnonymous;

	public SessionModelImpl(String name) {
		super(name);

	}

	@Override
	public void setSessionId(String sid) {
		this.setDefaultValue(sid);
	}

	@Override
	public String getSessionId() {
		// TODO Auto-generated method stub
		return (String) this.getDefaultValue();
	}

	@Override
	public void setAccount(String acc) {
		if (this.accountId == acc) {
			return;
		}
		this.accountId = acc;
		new AccountUpdateEvent(this, this.accountId).dispatch();
	}

	@Override
	public String getAccount() {
		return this.accountId;

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
	 * Nov 24, 2012
	 */
	@Override
	public void addAuthedHandler(EventHandlerI<ModelValueEvent> eh) {
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
		return this.isAnonymous;

	}

	/*
	 * Nov 25, 2012
	 */
	@Override
	public void setIsAnonymous(boolean an) {
		this.isAnonymous = an;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.session.SessionModelI#isAccount(java.
	 * lang.String)
	 */
	@Override
	public boolean isAccount(String accId) {
		return accId.equals(this.accountId);
	}

}
