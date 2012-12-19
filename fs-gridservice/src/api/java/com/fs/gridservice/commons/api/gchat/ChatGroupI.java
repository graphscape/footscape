/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.gchat;

import java.util.List;

import com.fs.gridservice.commons.api.gchat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.gchat.data.ParticipantGd;
import com.fs.gridservice.commons.api.presence.data.PresenceGd;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;

/**
 * @author wuzhen
 * 
 */
public interface ChatGroupI {

	public void dispatch(ChatMessageGd cm);

	public void addParticipant(String ptId);
	
	public void presence(PresenceGd pp);

	public List<ParticipantGd> getParticipantList();

	public TerminalGd getTerminalByParticipantId(String ptId);

}
