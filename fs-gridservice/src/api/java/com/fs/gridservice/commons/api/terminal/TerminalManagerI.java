/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.api.terminal;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;

/**
 * @author wuzhen
 * 
 */
public interface TerminalManagerI extends EntityGdManagerI<TerminalGd> {

	public TerminalGd getTerminal(String id);

	public TerminalGd getTerminal(String id, boolean force);

	public TerminalGd webSocketTerminal(WebSocketGoI wso);
	
	public TerminalGd web20Terminal(String address, PropertiesI<Object> pts);//

	public void sendMessage(String termId, MessageI msg);

	public void sendMessage(MessageI msg);

	public void sendTextMessage(String tId, String text);
	
	public void bindingClient(String tid, String clientId);
	
	public void bindingSession(String tid, String sid);

}