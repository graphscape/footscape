/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.api.gwt.client.chatrooms;

import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface ChatRoomsControlI extends ControlI {

	public ChatRoomControlI getChatRoomControl(String actId, boolean force);

}
