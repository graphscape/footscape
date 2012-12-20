/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.gchat;

import java.util.List;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.gchat.data.ChatGroupGd;
import com.fs.gridservice.commons.api.gchat.data.ParticipantGd;

/**
 * @author wuzhen
 * 
 */
public interface ChatGroupManagerI extends EntityGdManagerI<ChatGroupGd> {

	public ChatGroupGd createChatRoom(String id, PropertiesI<Object> pts);

	public ChatGroupGd getChatGroup(String id);

	public ChatGroupGd getChatGroup(String id, boolean force);

	public List<ParticipantGd> getParticipantList(String gid);

	public void addParticipant(ParticipantGd p);

	public ParticipantGd removeParticipant(String gid, String pid);

	public ParticipantManagerI getParticipantManager();

}
