/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.chat;

import java.util.List;

import com.fs.gridservice.commons.api.chat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.chat.data.ParticipantGd;
import com.fs.gridservice.commons.api.chat.data.PresenceGd;
import com.fs.gridservice.commons.api.chat.data.TerminalGd;

/**
 * @author wuzhen
 * 
 */
public interface ChatRoomI {

	public void dispatch(ChatMessageGd cm);

	public void addParticipant(String ptId);
	
	public void presence(PresenceGd pp);

	public List<ParticipantGd> getParticipantList();

	public TerminalGd getTerminalByParticipantId(String ptId);

}
