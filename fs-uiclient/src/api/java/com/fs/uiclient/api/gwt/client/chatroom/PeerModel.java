/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 24, 2012
 */
package com.fs.uiclient.api.gwt.client.chatroom;

import com.fs.uiclient.api.gwt.client.activity.ParticipantModel;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class PeerModel extends ModelSupport {

	public static final Location L_EXP_ID = Location.valueOf("expId");

	public static final Location L_XMPP_USER = Location.valueOf("xmppUser");

	public static final Location L_PRESENCE = Location.valueOf("presence");

	/**
	 * @param name
	 */
	public PeerModel(String name, String expId, String xmppUser) {
		super(name);
		this.setValue(L_EXP_ID, expId);
		this.setValue(L_XMPP_USER, xmppUser);
	}

	public boolean isExpId(String expId) {
		return this.getExpId().equals(expId);
	}

	public String getExpId() {
		return this.getValue(String.class, L_EXP_ID);
	}

	public String getXmppUser() {
		return this.getValue(String.class, L_XMPP_USER);
	}

	public ChatRoomModelI getChatRoom() {
		return (ChatRoomModelI) this.parent;
	}

	public ParticipantModel getParticipant() {// Not child,reference to
												// activity's child by exp.
		String expId = this.getExpId();
		return this.getChatRoom().getActivityModel()
				.getParticipantByExpId(expId);

	}

	public String getPresence() {
		return this.getValue(String.class, L_PRESENCE);
	}

	/**
	 * Oct 24, 2012
	 */
	public boolean isXmppUser(String xmppUser) {

		return this.getXmppUser().equals(xmppUser);//
	}

}
