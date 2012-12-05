/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.api.gwt.client.chatrooms;

import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomModelI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public interface ChatRoomsModelI extends ModelI {

	// TODO Jid upper case check?
	// Include the activity id.

	public static final String PREFIX_ACTIVITY = "ar-";// activity room,UPPER
														// case is not work,

	public static final String A_OPEN = "open";// open chat room for activity.

	public static final Location L_ACTIVITY_ID = Location.valueOf("activityID");// the
																				// activity
																				// to
																				// be
																				// open

	public ChatRoomModelI addChatRoom(String rname, String actId);

	public ChatRoomModelI getChatRoom(String rname, boolean force);

	public ChatRoomModelI getChatRoomByActivityId(String actId, boolean force);

}
