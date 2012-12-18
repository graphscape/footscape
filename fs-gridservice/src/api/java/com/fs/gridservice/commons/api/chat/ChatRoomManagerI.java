/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.chat;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.chat.data.ChatRoomGd;

/**
 * @author wuzhen
 *
 */
public interface ChatRoomManagerI {
	
	public ChatRoomGd createChatRoom(PropertiesI<Object> pts);
	
	public ChatRoomGd getChatRoom(String id);
	
}
