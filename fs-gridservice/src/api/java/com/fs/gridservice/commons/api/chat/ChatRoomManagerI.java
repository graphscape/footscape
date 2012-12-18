/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.chat;

import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public interface ChatRoomManagerI {

	public ChatRoomI createChatRoom(PropertiesI<Object> pts);

	public ChatRoomI getChatRoom(String id);

	public ChatRoomI getChatRoom(String id, boolean force);

}
