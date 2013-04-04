/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package com.fs.gridservice.commons.api.terminal;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.data.ClientGd;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;

/**
 * @author wu
 * 
 */
public class MessageSendingContext {

	private MessageI message;

	private TerminalGd terminal;

	private GridFacadeI facade;

	public MessageSendingContext(MessageI message, TerminalGd t, GridFacadeI facade) {
		this.message = message;
		this.terminal = t;
		this.facade = facade;
	}

	/**
	 * @return the message
	 */
	public MessageI getMessage() {
		return message;
	}

	/**
	 * @return the terminal
	 */
	public TerminalGd getTerminal() {
		return terminal;
	}

	/**
	 * @return the client
	 */
	public ClientGd getClient() {
		String cid = this.terminal.getClientId(false);
		if (cid == null) {
			return null;
		}
		return this.facade.getEntityManager(ClientManagerI.class).getEntity(cid);
	}

}
