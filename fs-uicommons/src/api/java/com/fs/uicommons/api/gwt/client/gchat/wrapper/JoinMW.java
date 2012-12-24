/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat.wrapper;

import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class JoinMW extends GChatMW {

	protected MessageData nested;

	/**
	 * @param md
	 */
	public JoinMW(MessageData md) {
		super(md);
		this.nested = (MessageData) md.getPayload("message", true);
	}

	public String getAccountId() {
		return this.nested.getString("accountId", true);
	}

	public String getJoinParticipantId() {
		return this.nested.getString("participantId", true);
	}

	public String getRole() {
		return this.nested.getString("role", true);
	}

	public String getNick() {
		return this.nested.getString("nick", true);
	}

}
