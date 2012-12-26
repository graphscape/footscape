/**
 *  Dec 26, 2012
 */
package com.fs.uicommons.api.gwt.client.session.event;

import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class AccountUpdateEvent extends Event {

	public static final Event.Type<AccountUpdateEvent> TYPE = new Event.Type<AccountUpdateEvent>();

	private String accountId;

	/**
	 * @param type
	 */
	public AccountUpdateEvent(SessionModelI sm, String accId) {
		super(TYPE, sm);
		this.accountId = accId;
	}

	public String getAccountId() {
		return accountId;
	}
}
