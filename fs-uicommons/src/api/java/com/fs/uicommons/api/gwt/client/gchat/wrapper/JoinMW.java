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

	/**
	 * @param md
	 */
	public JoinMW(MessageData md) {
		super(md);
	}

	public String getAccountId() {
		return this.target.getString("accountId", true);
	}

	public String getJoinParticipantId() {
		return this.target.getString("participantId", true);
	}

	public String getRole() {
		return this.target.getString("role", true);
	}

	public String getNick() {
		return this.target.getString("nick", true);
	}

}
