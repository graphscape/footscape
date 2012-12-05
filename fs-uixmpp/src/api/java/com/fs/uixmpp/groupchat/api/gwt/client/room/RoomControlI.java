/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client.room;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface RoomControlI extends ControlI {

	public void send(String message);

	// send the current editing message from model.
	// return the message send out.
	public String send();//
}
