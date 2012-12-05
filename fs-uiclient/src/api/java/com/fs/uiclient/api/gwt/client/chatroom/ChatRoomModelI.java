/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.api.gwt.client.chatroom;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;

/**
 * @author wu
 * 
 */
public interface ChatRoomModelI extends ModelI {

	public static final Location L_ACTIVITY_ID = Location.valueOf("activityId");

	public static final Location L_ROOMNAME = Location.valueOf("roomName");

	public static final Location L_LAST_MESSAGE_MODEL = Location
			.valueOf("lastMessageModelReceived");

	public static final Location L_LAST_PEER_MODEL_OF_PRESENCE_CHANGED = Location
			.valueOf("lastPeerModelOfPresenceChanged");

	public boolean isActivityId(String aid);

	public String getActivityId();

	public String getRoomName();

	public ActivityModelI getActivityModel();// not child,reference to

	public RoomModelI getRoomModel();// not child,reference to

	public PeerModel addPeer(String expId, String xmppUser);

	public PeerModel getPeerByXmppUser(String xmppUser, boolean force);

}
