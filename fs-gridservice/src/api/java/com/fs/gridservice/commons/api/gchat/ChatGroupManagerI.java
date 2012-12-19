/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.gchat;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.gchat.data.ChatGroupGd;

/**
 * @author wuzhen
 * 
 */
public interface ChatGroupManagerI extends EntityGdManagerI<ChatGroupGd> {

	public ChatGroupI createChatRoom(PropertiesI<Object> pts);

	public ChatGroupI getChatGroup(String id);

	public ChatGroupI getChatRoom(String id, boolean force);

}
