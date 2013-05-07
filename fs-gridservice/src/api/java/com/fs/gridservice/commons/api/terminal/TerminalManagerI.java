/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.api.terminal;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.gobject.EndPointGoI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;

/**
 * @author wuzhen
 * 
 */
public interface TerminalManagerI extends EntityGdManagerI<TerminalGd> {

	public void addBeforeMessageSendingHandler(HandlerI<MessageSendingContext> handler);

	public TerminalGd getTerminal(String id);

	public TerminalGd getTerminal(String id, boolean force);

	public TerminalGd createTerminal(EndPointGoI wso);

	public void sendMessage(String termId, MessageI msg);

	public void sendMessage(MessageI msg);

	public void sendTextMessage(String tId, String text);

	public void bindingClient(String tid, String clientId);

	public void bindingSession(String tid, String sid);

	public void unBindingSession(String tid);

	public TerminalGd getTerminalBySessionId(String sid, boolean force);

}
